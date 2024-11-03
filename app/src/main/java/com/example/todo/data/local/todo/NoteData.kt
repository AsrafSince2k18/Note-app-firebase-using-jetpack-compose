package com.example.todo.data.local.todo

import androidx.compose.ui.graphics.Color

data class NoteData(

    val id : String?=null,

    val userId : String?=null,

    val title : String="",

    val subTitle: String="",

    val content : String="",

    val dateTime : Long =System.currentTimeMillis(),

    val color : Int = 0,

    val imageList : ArrayList<String> = arrayListOf()

)
