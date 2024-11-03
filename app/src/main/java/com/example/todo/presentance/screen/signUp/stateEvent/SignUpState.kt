package com.example.todo.presentance.screen.signUp.stateEvent

data class SignUpState(

    val email:String="",

    val password:String="",

    val conformPassword : String="",

    val isEmailError : String?=null,

    val isPasswordError:String?=null,

    val isConformPasswordError:String?=null,

    val firebaseError:String?=null,

    val userValid:Boolean=false,

    val signBtnLoading:Boolean=false

)
