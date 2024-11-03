package com.example.todo.presentance.screen.homeScreen.stateEvent

import com.example.todo.data.local.todo.NoteData

data class HomeState(

    val noteItemList : List<NoteData> = emptyList(),
    val searchItemList : List<NoteData> = emptyList(),

    val search:String=""
)