package com.example.taskn20.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskn20.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity WHERE id LIKE :id")
    suspend fun findById(id: Int): UserEntity

    @Query("SELECT * FROM UserEntity WHERE email LIKE :email AND firstName LIKE :firstName AND lastName LIKE :lastName AND age LIKE :age")
    suspend fun findByEmail(
        firstName: String,
        lastName: String,
        age: Int,
        email: String
    ): UserEntity?

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)
}