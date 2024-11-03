package com.example.todo.di.validaction

import com.example.todo.domain.useCase.auth.signIn.SignInEmail
import com.example.todo.domain.useCase.auth.signIn.SignInPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignInModule {

    @Provides
    @Singleton
    fun provideSignInEmail()=SignInEmail()

    @Provides
    @Singleton
    fun provideSignInPassword()=SignInPassword()

}