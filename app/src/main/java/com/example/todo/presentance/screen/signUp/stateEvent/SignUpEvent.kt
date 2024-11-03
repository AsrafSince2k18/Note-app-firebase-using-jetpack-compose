package com.example.todo.presentance.screen.signUp.stateEvent

sealed class SignUpEvent {

    data class Email(val email:String) : SignUpEvent()
    data class Password(val password:String) : SignUpEvent()
    data class ConformPassword(val cPassword:String) : SignUpEvent()

    data object SignUpBtn : SignUpEvent()

}