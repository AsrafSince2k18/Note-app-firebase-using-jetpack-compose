package com.example.todo.presentance.screen.writeScreen.viewModel

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColor
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.local.todo.NoteData
import com.example.todo.domain.repository.firestoreRepo.FireStoreRepo
import com.example.todo.presentance.screen.writeScreen.gallery.state.GalleryImageHolder
import com.example.todo.presentance.screen.writeScreen.gallery.state.GalleryState
import com.example.todo.presentance.screen.writeScreen.stateEvent.WriteEvent
import com.example.todo.presentance.screen.writeScreen.stateEvent.WriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import javax.inject.Inject


@HiltViewModel
class WriteViewModel @Inject constructor(
    private val fireStoreRepo: FireStoreRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _writeState = MutableStateFlow(WriteState())
    val writeState = _writeState.asStateFlow()

    init {
        fetchData()
    }

    fun onEvent(event: WriteEvent) {
        when (event) {
            is WriteEvent.ZoneDate -> {
                _writeState.update {
                    it.copy(zoneTimeDate = event.timeDate)
                }
            }

            is WriteEvent.SubTitle -> {
                _writeState.update {
                    it.copy(subTitle = event.subTitle)
                }
            }

            is WriteEvent.Title -> {
                _writeState.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            is WriteEvent.Content -> {
                _writeState.update {
                    it.copy(content = event.content)
                }
            }

            is WriteEvent.ColorChange -> {
                _writeState.update {
                    it.copy(color = event.color)
                }
            }

            is WriteEvent.ImageUri -> {
                _writeState.update {
                    it.copy(imageUri = event.image)
                }
            }

            WriteEvent.DeleteBtn -> {
                deleteBtn()
            }
            WriteEvent.SaveBtn -> {
                insertOrUpdate()
            }

            is WriteEvent.TimeDate -> {
                _writeState.update {
                    it.copy(timeDate = event.timeDate)
                }
            }

            is WriteEvent.UriAndImageType -> {
                uriAndImageType(uri=event.uri, imageType = event.imageType)
            }
        }
    }

    @SuppressLint("NewApi")
    private fun fetchData(){
        viewModelScope.launch {
            _writeState.update {
                it.copy(id=savedStateHandle["id"])
            }
            if(writeState.value.id!=null){
                fireStoreRepo.fetchData(
                    id=writeState.value.id!!,
                    onSuccess = {noteData->
                       if(noteData!=null){
                           _writeState.update {
                               it.copy(
                                   title = noteData.title,
                                   content = noteData.content,
                                   timeDate = noteData.dateTime,
                                   subTitle = noteData.subTitle,
                                   color = Color(noteData.color),
                                   noteData = noteData
                               )
                           }
                       }
                    },
                    onError = {
                        throw IllegalArgumentException(it.message)
                        Log.d("e1", "fetchData: ${it.message}")
                    }
                )
            }

        }
    }


    @SuppressLint("NewApi")
    private fun insertOrUpdate() {
        viewModelScope.launch {

            if (writeState.value.noteData != null) {
                withContext(Dispatchers.IO) {
                    writeState.value.id?.let {noteId->
                        fireStoreRepo.updateData(
                            id = noteId,
                            noteData = NoteData(
                                id =noteId,
                                userId = fireStoreRepo.currentUser!!,
                                title = writeState.value.title,
                                subTitle = writeState.value.subTitle,
                                content = writeState.value.content,
                                color = writeState.value.color.toArgb(),
                                dateTime = writeState.value.timeDate,
                                imageList = arrayListOf()
                            )

                        )
                    }
                }

            }else {
                withContext(Dispatchers.IO) {
                    fireStoreRepo.insertDate(
                        noteData = NoteData(
                            id = fireStoreRepo.documentId,
                            userId = fireStoreRepo.currentUser!!,
                            subTitle = writeState.value.subTitle,
                            title = writeState.value.title,
                            content = writeState.value.content,
                            color = writeState.value.color.toArgb(),
                            dateTime = writeState.value.timeDate,
                            imageList = arrayListOf()
                        )
                    )
                }
            }
        }
    }


    private fun deleteBtn() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (writeState.value.id != null) {
                    fireStoreRepo.deleteData(writeState.value.id!!)
                }
            }
        }
    }


    private fun uriAndImageType(uri: Uri,imageType:String){

        val remotePath = "images/${fireStoreRepo.currentUser}/${uri.lastPathSegment}-${System.currentTimeMillis()}/$imageType"

        writeState.value.galleryState.addImage(
            galleryImageHolder = GalleryImageHolder(
                remotePath=remotePath,
                imageUri = uri
            )
        )

        Log.d("t1", "uriAndImageType Size: ${writeState.value.galleryState.galleryState.size}")

    }

}