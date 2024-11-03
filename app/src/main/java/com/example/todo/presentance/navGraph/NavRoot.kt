package com.example.todo.presentance.navGraph

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoot {

    @Serializable
    data object SignInRoot : NavRoot()
    @Serializable
    data object SignUpRoot : NavRoot()
    @Serializable
    data object HomeScreenRoot : NavRoot()

    @Serializable
    data class WriteScreenRoot(val id :String?=null)


}