package com.example.taskn20.presentation.screen.update_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskn20.domain.usecase.user.FindUserByIdUseCase
import com.example.taskn20.domain.usecase.user.UpdateUserUseCase
import com.example.taskn20.presentation.event.UpdateUserEvent
import com.example.taskn20.presentation.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    private val findUserByIdUseCase: FindUserByIdUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _userStateFlow = MutableStateFlow(User(firstName = "", lastName = "", age = 0, email = ""))

    private val _uiEvent = MutableSharedFlow<UpdateUserUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: UpdateUserEvent) {
        when (event) {
            is UpdateUserEvent.FindUserByIdEvent -> findUserById(id = event.id)
            is UpdateUserEvent.UpdateUser -> updateUser(
                event.firstName,
                event.lastName,
                event.age,
                event.email
            )

            is UpdateUserEvent.GoBacKToUserFragmentEvent -> goBackToUserFragment()
        }
    }

    private fun findUserById(id: Int) {
        viewModelScope.launch {
            _userStateFlow.value = findUserByIdUseCase(id)
        }
    }

    private fun updateUser(firstName: String, lastName: String, age: String, email: String) {
        viewModelScope.launch {
            updateUserUseCase(
                User(
                    _userStateFlow.value.id,
                    firstName,
                    lastName,
                    age.toInt(),
                    email
                )
            )
            _uiEvent.emit(UpdateUserUiEvent.GoToUserFragmentEvent)
        }
    }

    private fun goBackToUserFragment() {
        viewModelScope.launch {
            _uiEvent.emit(UpdateUserUiEvent.GoToUserFragmentEvent)
        }
    }

    sealed interface UpdateUserUiEvent {
        data object GoToUserFragmentEvent : UpdateUserUiEvent
    }
}