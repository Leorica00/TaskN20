package com.example.taskn20.domain.usecase.validation

class EmptyFieldsValidationUseCase {
    operator fun invoke(vararg field: String): Boolean {
        return field.all { it.isNotBlank() }
    }
}