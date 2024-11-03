package com.example.todo.data.repoImpl.firestoreRepoImpl

import android.util.Log
import com.example.todo.data.local.todo.NoteData
import com.example.todo.domain.repository.firestoreRepo.FireStoreRepo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FireStoreRepoImpl(
    private val firebaseFirestore: FirebaseFirestore
) : FireStoreRepo {

    private val noteCollection = firebaseFirestore.collection("notes")


    override val documentId: String
        get() = noteCollection.document().id

    override val currentUser: String?
        get() = Firebase.auth.currentUser?.uid


    override suspend fun insertDate(noteData: NoteData) {
        try {
            noteCollection.document(noteData.id!!)
                .set(noteData)
        } catch (e: FirebaseFirestoreException) {
            Log.d("e1", "insertDate: ${e.message}")
        }
    }

    override suspend fun updateData(id: String, noteData: NoteData) {

        val updateData = hashMapOf<String, Any>(
            "title" to noteData.title,
            "content" to noteData.content,
            "subTitle" to noteData.subTitle,
            "color" to noteData.color,
            "dateTime" to noteData.dateTime,
            "imageList" to noteData.imageList
        )

        noteCollection.document(id)
            .update(updateData)

    }

    override suspend fun deleteData(id: String) {
        noteCollection.document(id)
            .delete()
    }

    override suspend fun fetchData(
        id: String,
        onSuccess: (NoteData?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        noteCollection.document(id)
            .get()
            .addOnCompleteListener {
                val data = it.result.toObject(NoteData::class.java)
                onSuccess(data)
            }
    }

    override fun getAllData(): Flow<List<NoteData>> {
        return noteCollection.whereEqualTo("userId", currentUser)
            .dataObjects()
    }


    override fun clearAllData() {

        noteCollection.whereEqualTo("userId", currentUser)
            .addSnapshotListener { value, error ->
                try {
                    value?.forEach {
                        it.data.forEach { _ ->
                            clearAllData()
                        }
                    }
                }catch (e:Exception){
                    throw IllegalArgumentException("${error?.message}")
                }
            }


    }

    override fun searchData(search: String): Flow<List<NoteData>> {
        return noteCollection.whereEqualTo("userId", currentUser)
            .whereEqualTo("title", search)
            .dataObjects()
    }


}