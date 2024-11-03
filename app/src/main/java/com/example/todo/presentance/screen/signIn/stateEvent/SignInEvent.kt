package com.example.todo.presentance.screen.signIn.stateEvent

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthCredential

sealed class SignInEvent {

    data class Email(val email:String) : SignInEvent()

    data class Password(val password:String):SignInEvent()

    data class GoogleAuth(val authCredential: AuthCredential):SignInEvent()



    data object SignInBtn : SignInEvent()

    data object SignInGBtn : SignInEvent()

}