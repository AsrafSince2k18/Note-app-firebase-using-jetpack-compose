package com.example.todo.domain.useCase.auth.signUp

import com.example.todo.data.local.auth.Validation

class SignUpConformPassword {

    fun invoke(conformPassword:String,password:String): Validation {
        if(conformPassword.isBlank()){
            return Validation(
                success = false,
                isError = "Enter the conformPassword"
            )
        }


        if(conformPassword!=password){
            return Validation(
                success = false,
                isError = "Conform Password doesn't match."
            )
        }



        return Validation(success = true)

    }

}