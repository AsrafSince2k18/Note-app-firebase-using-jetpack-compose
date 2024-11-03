package com.example.todo.domain.repository.firestoreRepo

import com.example.todo.data.local.todo.NoteData
import kotlinx.coroutines.flow.Flow

interface FireStoreRepo {

    suspend fun insertDate(
        noteData: NoteData
    )

    suspend fun updateData(
        id : String,
        noteData: NoteData
    )

    suspend fun deleteData(id:String)

    suspend fun fetchData(
        id:String,
        onSuccess : (NoteData?) -> Unit,
        onError : (Exception) -> Unit
    )

    fun getAllData() : Flow<List<NoteData>>

     fun clearAllData()

    fun searchData(search:String) : Flow<List<NoteData>>

    val documentId : String?
    val currentUser : String?

}