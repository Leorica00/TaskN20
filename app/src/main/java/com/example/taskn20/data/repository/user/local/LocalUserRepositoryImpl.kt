package com.example.taskn20.data.repository.user.local

import com.example.taskn20.data.local.dao.UserDao
import com.example.taskn20.data.local.mapper.toData
import com.example.taskn20.data.local.mapper.toDomain
import com.example.taskn20.domain.model.GetUser
import com.example.taskn20.domain.repository.user.LocalUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(private val userDao: UserDao) :
    LocalUserRepository {

    override fun getAll(): Flow<List<GetUser>> {
        return userDao.getAll().map { users ->
            users.map {
                it.toDomain()
            }
        }
    }

    override suspend fun findByEmail(firstName:String, lastName: String, age: Int, email: String): GetUser? {
        return userDao.findByEmail(firstName, lastName, age, email)?.toDomain()
    }

    override suspend fun findById(id: Int): GetUser {
        return userDao.findById(id).toDomain()
    }

    override suspend fun insertUser(user: GetUser) {
        userDao.insertUser(user = user.toData())
    }

    override suspend fun deleteUser(user: GetUser) {
        userDao.deleteUser(user = user.toData())
    }

    override suspend fun updateUser(user: GetUser) {
        userDao.updateUser(user = user.toData())
    }
}