package com.example.taskn20.di

import com.example.taskn20.data.local.dao.UserDao
import com.example.taskn20.data.repository.user.local.LocalUserRepositoryImpl
import com.example.taskn20.domain.repository.user.LocalUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLocalUserRepository(userDao: UserDao): LocalUserRepository = LocalUserRepositoryImpl(userDao)
}