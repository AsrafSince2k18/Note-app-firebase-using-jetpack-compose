package com.example.todo.di.validaction

import com.example.todo.domain.useCase.auth.signUp.SignUpConformPassword
import com.example.todo.domain.useCase.auth.signUp.SignUpEmail
import com.example.todo.domain.useCase.auth.signUp.SignUpPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object SignUpModule {

    @Provides
    @Singleton
    fun provideSignUpEmail()=SignUpEmail()

    @Provides
    @Singleton
    fun provideSignUpPassword()=SignUpPassword()

    @Provides
    @Singleton
    fun provideSignUpConformPassword() = SignUpConformPassword()

}