package com.example.taskn20.domain.usecase.user

import com.example.taskn20.domain.repository.user.LocalUserRepository
import com.example.taskn20.presentation.mapper.toPresenter
import com.example.taskn20.presentation.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val localUserRepository: LocalUserRepository) {
    operator fun invoke(): Flow<List<User>> {
        return localUserRepository.getAll().map {users ->
            users.map {
                it.toPresenter()
            }
        }
    }
}