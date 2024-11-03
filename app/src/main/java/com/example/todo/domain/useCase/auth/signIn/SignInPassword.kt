package com.example.todo.domain.useCase.auth.signIn

import com.example.todo.data.local.auth.Validation

class SignInPassword {

    fun invoke(password:String):Validation{
        if(password.isBlank()){
            return Validation(
                success = false,
                isError = "Enter the password"
            )
        }

        return Validation(success = true)

    }

}