package com.example.todo.presentance.screen.writeScreen.stateEvent

import android.net.Uri
import androidx.compose.ui.graphics.Color
import java.time.ZonedDateTime

sealed class WriteEvent {

    data class Title(val title : String) : WriteEvent()
    data class SubTitle(val subTitle : String) : WriteEvent()

    data class Content(val content :String) : WriteEvent()

    data class ColorChange(val color: Color) : WriteEvent()

    data class ZoneDate(val timeDate : ZonedDateTime) : WriteEvent()
    data class TimeDate(val timeDate : Long) : WriteEvent()
    data class ImageUri(val image : Uri) : WriteEvent()

    data class UriAndImageType(val uri : Uri,val imageType : String) : WriteEvent()

    data object SaveBtn :WriteEvent()
    data object DeleteBtn : WriteEvent()

}