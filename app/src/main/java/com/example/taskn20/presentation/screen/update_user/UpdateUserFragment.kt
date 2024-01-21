package com.example.taskn20.presentation.screen.update_user

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskn20.databinding.FragmentUpdateUserBinding
import com.example.taskn20.presentation.base.BaseFragment
import com.example.taskn20.presentation.event.UpdateUserEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateUserFragment :
    BaseFragment<FragmentUpdateUserBinding>(FragmentUpdateUserBinding::inflate) {

    private val viewModel: UpdateUserViewModel by viewModels()
    private val args: UpdateUserFragmentArgs by navArgs()

    override fun setUp() {
        viewModel.onEvent(UpdateUserEvent.FindUserByIdEvent(args.id))
    }

    override fun setUpListeners() {
        updateUserButtonClickListener()
        goBackButtonClickListener()
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleUiEvent(it)
                }
            }
        }
    }

    private fun updateUserButtonClickListener( ){
        with(binding) {
            btnUpdateUser.setOnClickListener {
                viewModel.onEvent(UpdateUserEvent.UpdateUser(
                    etFirstName.text.toString(),
                    etLastName.text.toString(),
                    etAge.text.toString(),
                    etEmail.text.toString()
                ))
            }
        }
    }

    private fun goBackButtonClickListener() {
        binding.btnGoBack.setOnClickListener {
            viewModel.onEvent(UpdateUserEvent.GoBacKToUserFragmentEvent)
        }
    }

    private fun handleUiEvent(event: UpdateUserViewModel.UpdateUserUiEvent) {
        when (event) {
            is UpdateUserViewModel.UpdateUserUiEvent.GoToUserFragmentEvent -> findNavController().navigateUp()
        }
    }

}