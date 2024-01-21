package com.example.taskn20.domain.usecase.user

import com.example.taskn20.domain.repository.user.LocalUserRepository
import com.example.taskn20.presentation.mapper.toDomain
import com.example.taskn20.presentation.model.User
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val localUserRepository: LocalUserRepository) {
    suspend operator fun invoke(user: User) = localUserRepository.updateUser(user = user.toDomain())
}