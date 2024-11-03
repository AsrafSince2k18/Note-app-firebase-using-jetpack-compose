package com.example.todo.presentance.screen.homeScreen.stateEvent

sealed class HomeEvent {

    data class Search(val search:String) : HomeEvent()

    data object ClearAllData : HomeEvent()
    data object SearchBtn : HomeEvent()

}