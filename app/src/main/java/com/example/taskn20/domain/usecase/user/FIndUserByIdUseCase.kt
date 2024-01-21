package com.example.taskn20.domain.usecase.user

import com.example.taskn20.domain.repository.user.LocalUserRepository
import com.example.taskn20.presentation.mapper.toPresenter
import com.example.taskn20.presentation.model.User
import javax.inject.Inject

class FindUserByIdUseCase @Inject constructor(private val localUserRepository: LocalUserRepository){
    suspend operator fun invoke(id: Int): User {
        return localUserRepository.findById(id).toPresenter()
    }
}