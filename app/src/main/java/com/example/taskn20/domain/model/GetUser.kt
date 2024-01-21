package com.example.taskn20.domain.model

data class GetUser(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String
)