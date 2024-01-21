package com.example.taskn20.domain.usecase.user

import com.example.taskn20.domain.repository.user.LocalUserRepository
import com.example.taskn20.presentation.mapper.toPresenter
import com.example.taskn20.presentation.model.User
import javax.inject.Inject

class FindUserUseCase @Inject constructor(private val localUserRepository: LocalUserRepository){
    suspend operator fun invoke(firstName:String, lastName: String, age: Int, email: String): User? {
        return localUserRepository.findByEmail(firstName, lastName, age, email)?.toPresenter()
    }
}