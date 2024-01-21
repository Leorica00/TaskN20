package com.example.taskn20.presentation.event

sealed class UpdateUserEvent {
    data class FindUserByIdEvent(val id: Int): UpdateUserEvent()
    data class UpdateUser(val firstName: String, val lastName: String, val age: String, val email: String): UpdateUserEvent()
    data object GoBacKToUserFragmentEvent : UpdateUserEvent()
}