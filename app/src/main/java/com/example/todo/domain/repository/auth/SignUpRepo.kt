package com.example.todo.domain.repository.auth

interface SignUpRepo {

    suspend fun createNewUser(
        email:String,
        password:String,
        isSuccess : (Boolean) -> Unit,
        isError : (Exception) -> Unit,
    )

}