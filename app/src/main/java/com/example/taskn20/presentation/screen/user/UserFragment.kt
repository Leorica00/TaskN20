package com.example.taskn20.presentation.screen.user

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.taskn20.databinding.FragmentUserBinding
import com.example.taskn20.presentation.base.BaseFragment
import com.example.taskn20.presentation.event.UserEvent
import com.example.taskn20.presentation.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val userViewModel: UserViewModel by viewModels()
    override fun setUp() {
    }

    override fun setUpListeners() {
        onAddButtonClickListener()
        onRemoveButtonClickListener()
        onUpdateButtonClickListener()
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    userViewModel.usersFlow.collect {
                        binding.tvActiveUsersNumber.text = it.size.toString()
                    }
                }

                launch {
                    userViewModel.userValidationStateFlow.collect {
                        binding.tvResponce.text = it
                    }
                }

                launch {
                    userViewModel.uiEvent.collect {
                        handleUiEvent(it)
                    }
                }
            }
        }
    }

    private fun onAddButtonClickListener() {
        binding.btnAddUser.setOnClickListener {
            userViewModel.onEvent(UserEvent.CreateUser(
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etAge.text.toString(),
                binding.etEmail.text.toString()
            ))
        }
    }

    private fun onRemoveButtonClickListener() {
        binding.btnRemoveUser.setOnClickListener {
            userViewModel.onEvent(UserEvent.DeleteUser(
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etAge.text.toString(),
                binding.etEmail.text.toString()
            ))
        }
    }

    private fun onUpdateButtonClickListener() {
        binding.btnUpdateUser.setOnClickListener {
            userViewModel.onEvent(UserEvent.GetUserByInputs(
                binding.etFirstName.text.toString(),
                binding.etLastName.text.toString(),
                binding.etAge.text.toString(),
                binding.etEmail.text.toString()
            ))
        }
    }

    private fun handleUiEvent(event: UserViewModel.UserUiEvent) {
        when(event) {
            is UserViewModel.UserUiEvent.GoToUpdateUserFragmentEvent -> findNavController().navigate(UserFragmentDirections.actionUserFragmentToUpdateUserFragment(id = event.id!!))
        }
    }
}