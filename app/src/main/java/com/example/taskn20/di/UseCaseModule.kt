package com.example.taskn20.di

import com.example.taskn20.domain.usecase.validation.EmailValidationUseCase
import com.example.taskn20.domain.usecase.validation.EmptyFieldsValidationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideEmailValidationUseCase() = EmailValidationUseCase()

    @Provides
    @Singleton
    fun provideEmptyFieldsValidationUseCase() = EmptyFieldsValidationUseCase()
}