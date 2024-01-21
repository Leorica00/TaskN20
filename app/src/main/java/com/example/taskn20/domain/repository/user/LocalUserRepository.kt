package com.example.taskn20.domain.repository.user

import com.example.taskn20.domain.model.GetUser
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    fun getAll(): Flow<List<GetUser>>

    suspend fun findByEmail(firstName: String, lastName: String, age: Int, email: String): GetUser?

    suspend fun findById(id: Int): GetUser

    suspend fun insertUser(user: GetUser)

    suspend fun deleteUser(user: GetUser)

    suspend fun updateUser(user: GetUser)

}