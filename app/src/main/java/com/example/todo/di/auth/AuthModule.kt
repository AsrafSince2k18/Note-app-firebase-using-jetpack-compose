package com.example.todo.di.auth

import com.example.todo.data.repoImpl.auth.SignInRepoImpl
import com.example.todo.data.repoImpl.auth.SignUpRepoImpl
import com.example.todo.domain.repository.auth.SignInRepo
import com.example.todo.domain.repository.auth.SignUpRepo
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideSignUpRepoImpl(firebaseAuth: FirebaseAuth):SignUpRepo{
        return SignUpRepoImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideSignInRepoImpl(firebaseAuth: FirebaseAuth):SignInRepo{
        return SignInRepoImpl(firebaseAuth = firebaseAuth)
    }

}