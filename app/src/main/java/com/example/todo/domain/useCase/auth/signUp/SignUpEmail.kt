package com.example.todo.domain.useCase.auth.signUp

import android.util.Patterns
import com.example.todo.data.local.auth.Validation
import java.util.regex.Pattern

class SignUpEmail{

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
