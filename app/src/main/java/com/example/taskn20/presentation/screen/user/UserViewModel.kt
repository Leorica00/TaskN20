package com.example.taskn20.presentation.screen.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskn20.domain.usecase.user.CreateUserUseCase
import com.example.taskn20.domain.usecase.user.DeleteUserUseCase
import com.example.taskn20.domain.usecase.user.FindUserUseCase
import com.example.taskn20.domain.usecase.user.GetAllUsersUseCase
import com.example.taskn20.domain.usecase.user.UpdateUserUseCase
import com.example.taskn20.domain.usecase.validation.EmailValidationUseCase
import com.example.taskn20.domain.usecase.validation.EmptyFieldsValidationUseCase
import com.example.taskn20.presentation.event.UserEvent
import com.example.taskn20.presentation.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    getAllUsersUseCase: GetAllUsersUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val findUserUser: FindUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val emptyFieldsValidationUseCase: EmptyFieldsValidationUseCase
) : ViewModel() {

    private val _usersFlow: Flow<List<User>> = getAllUsersUseCase()
    val usersFlow = _usersFlow

    private val _userValidationStateFlow = MutableStateFlow("")
    val userValidationStateFlow = _userValidationStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UserUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.CreateUser -> createUser(event.firstName, event.lastName, event.age, event.email)

            is UserEvent.DeleteUser -> deleteUser(event.firstName, event.lastName, event.age, event.email)

            is UserEvent.GetUserByInputs -> checkIfUserExists(event.firstName, event.lastName, event.age, event.email)

            is UserEvent.UpdateUser -> updateUser(event.firstName, event.lastName, event.age, event.email, event.findUser)
        }
    }

    private fun checkIfUserExists(firstName: String, lastName: String, age: String, email: String) {
        if (validateForm(firstName, lastName, age, email)) {
            viewModelScope.launch {
                val user = getUserByInputs(firstName, lastName, age.toInt(), email)
                if (user == null) {
                    _userValidationStateFlow.value = "User does not exist"
                }else {
                    _uiEvent.emit(UserUiEvent.GoToUpdateUserFragmentEvent(user.id))
                }
            }
        }
    }

    private fun updateUser(firstName: String, lastName: String, age: String, email: String, chosenUser: User) {
        if (validateForm(firstName, lastName, age, email)) {
            viewModelScope.launch {
                val user = getUserByInputs(firstName, lastName, age.toInt(), email)
                if (user == null) {
                    updateUserUseCase(User(id = chosenUser.id, firstName = firstName, lastName = lastName, age = age.toInt(), email = email))
                    _userValidationStateFlow.value = "User added successfully"
                } else {
                    _userValidationStateFlow.value = "User already exists"
                }
            }
        }
    }

    private fun createUser(firstName: String, lastName: String, age: String, email: String) {
        if (validateForm(firstName, lastName, age, email)) {
            viewModelScope.launch {
                val user = getUserByInputs(firstName, lastName, age.toInt(), email)
                if (user == null) {
                    createUserUseCase(User(firstName = firstName, lastName = lastName, age = age.toInt(), email = email))
                    _userValidationStateFlow.value = "User added successfully"
                } else {
                    _userValidationStateFlow.value = "User already exists"
                }
            }
        }
    }

    private fun deleteUser(firstName: String, lastName: String, age: String, email: String) {
        if (validateForm(firstName, lastName, age, email)) {
            viewModelScope.launch {
                val user = getUserByInputs(firstName, lastName, age.toInt(), email)
                if (user != null) {
                    deleteUserUseCase(User(user.id, firstName, lastName, age.toInt(), email))
                    _userValidationStateFlow.value = "User Deleted successfully"
                } else {
                    _userValidationStateFlow.value = "User does not exist"
                }
            }
        }
    }

    private suspend fun getUserByInputs(firstName:String, lastName: String, age: Int, email: String): User? {
        return findUserUser(firstName, lastName, age, email)
    }

    private fun validateForm(firstName: String, lastName: String, age: String, email: String): Boolean {
        return if (!emptyFieldsValidationUseCase(firstName, lastName, age, email)) {
            _userValidationStateFlow.value = "fill all fields"
            false
        } else if (!emailValidationUseCase(email)) {
            _userValidationStateFlow.value = "Email is not valid"
            false
        } else {
            true
        }
    }

    sealed interface UserUiEvent {
        data class GoToUpdateUserFragmentEvent(val id: Int?) : UserUiEvent
    }

}