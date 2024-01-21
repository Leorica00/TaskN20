package com.example.taskn20.presentation.event

import com.example.taskn20.presentation.model.User

sealed class UserEvent {
    data class CreateUser(val firstName: String, val lastName: String, val age: String, val email: String): UserEvent()
    data class DeleteUser(val firstName: String, val lastName: String, val age: String, val email: String): UserEvent()
    data class GetUserByInputs(val firstName: String, val lastName: String, val age: String, val email: String): UserEvent()
    data class UpdateUser(val firstName: String, val lastName: String, val age: String, val email: String, val findUser: User): UserEvent()
}