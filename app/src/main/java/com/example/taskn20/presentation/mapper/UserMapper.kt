package com.example.taskn20.presentation.mapper

import com.example.taskn20.domain.model.GetUser
import com.example.taskn20.presentation.model.User


fun GetUser.toPresenter() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    age = age,
    email = email
)

fun User.toDomain() = GetUser(
    id = id,
    firstName = firstName,
    lastName = lastName,
    age = age,
    email = email
)