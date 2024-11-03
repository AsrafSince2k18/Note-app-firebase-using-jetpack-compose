package com.example.todo.domain.repository.auth

interface SignInRepo {

    suspend fun signInUser(
        email:String,
        password:String,
        isSuccess : (Boolean) -> Unit,
        isError : (Exception) -> Unit,
    )

}