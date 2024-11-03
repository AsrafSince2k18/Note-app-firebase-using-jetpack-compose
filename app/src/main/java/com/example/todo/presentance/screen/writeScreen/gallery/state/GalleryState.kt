package com.example.todo.presentance.screen.writeScreen.gallery.state

import androidx.compose.runtime.mutableStateListOf

class GalleryState {

    var galleryState = mutableStateListOf<GalleryImageHolder>()

    fun addImage(galleryImageHolder: GalleryImageHolder){
        galleryState.add(galleryImageHolder)
    }

    fun deleteImage(galleryImageHolder: GalleryImageHolder){
        galleryState.remove(galleryImageHolder)
    }

    fun clearAllImage(){
        galleryState.clear()
    }

}