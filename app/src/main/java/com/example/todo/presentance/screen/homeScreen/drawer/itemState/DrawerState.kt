package com.example.todo.presentance.screen.homeScreen.drawer.itemState

enum class DrawerState {
    Opened,Closed
}

fun DrawerState.toOpen():Boolean{
    return this.name == "Opened"
}

fun DrawerState.toOpposite():DrawerState{
    return if(this.name=="Opened") DrawerState.Closed
    else DrawerState.Opened
}



