package com.example.todo.domain.useCase.auth.signIn

import android.util.Patterns
import com.example.todo.data.local.auth.Validation
import java.util.regex.Pattern

class SignInEmail{

    fun invoke(email:String):Validation{

        if(email.isBlank()){
            return Validation(
                success = false,
                isError = "Enter the email"
            )
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return Validation(
                success = false,
                isError = "Invalid email format"
            )
        }

        return Validation(success = true)


    }

}
