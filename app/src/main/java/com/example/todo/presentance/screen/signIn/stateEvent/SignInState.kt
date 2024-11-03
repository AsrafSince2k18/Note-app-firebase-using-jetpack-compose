package com.example.todo.presentance.screen.signIn.stateEvent

data class SignInState(

    val email : String="",

    val password : String="",

    val isEmailError : String?=null,

    val isPasswordError:String?=null,

    val firebaseError:String?=null,

    val signInBtnLoading : Boolean=false,

    val gBtnLoading:Boolean=false,

    val userValid:Boolean=false
)
