package com.example.taskn20.data.local.mapper

import com.example.taskn20.data.local.entity.UserEntity
import com.example.taskn20.domain.model.GetUser

fun UserEntity.toDomain() = GetUser(
    id = id,
    firstName = firstName,
    lastName = lastName,
    age = age,
    email = email
)

fun GetUser.toData(): UserEntity {
    return if (id != null) {
        UserEntity(
            id = id,
            firstName = firstName,
            lastName = lastName,
            age = age,
            email = email
        )
    }else {
        UserEntity(
            firstName = firstName,
            lastName = lastName,
            age = age,
            email = email
        )
    }
}