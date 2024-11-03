package com.example.todo.presentance.screen.writeScreen.stateEvent

import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.example.todo.data.local.todo.NoteData
import com.example.todo.presentance.screen.writeScreen.gallery.state.GalleryImageHolder
import com.example.todo.presentance.screen.writeScreen.gallery.state.GalleryState
import com.example.todo.presentance.utils.colorsItem
import java.time.ZonedDateTime

data class WriteState(

    val title : String="",
    val subTitle : String="",
    val content : String="",

    val id: String?=null,

    val noteData: NoteData?=null,

    val color : Color = colorsItem[0],

    val zoneTimeDate : ZonedDateTime = ZonedDateTime.now(),

    val timeDate : Long = System.currentTimeMillis(),

    val galleryState: GalleryState = GalleryState(),

    val imageUri : Uri = Uri.EMPTY,

    val galleryImageHolder: GalleryImageHolder = GalleryImageHolder(remotePath = "", imageUri = Uri.EMPTY)

)
