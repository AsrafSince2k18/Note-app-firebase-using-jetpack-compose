package com.example.todo.domain.useCase.auth.signUp

import com.example.todo.data.local.auth.Validation

class SignUpPassword {

    fun invoke(password:String):Validation{
        if(password.isBlank()){
            return Validation(
                success = false,
                isError = "Enter the password"
            )
        }

        if(password.length<6){
            return Validation(
                success = false,
                isError = "Password must be at least 6 characters."
            )
        }

        val passwordContains=password.any { it.isDigit() && it.isLetter() }

        if(passwordContains){
            return Validation(
                success = false,
                isError = "Password must contain at least one letter or digit."
            )
        }

        return Validation(success = true)

    }

}