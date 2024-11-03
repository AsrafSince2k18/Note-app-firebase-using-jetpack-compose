package com.example.todo.di.firebase

import com.example.todo.data.repoImpl.firestoreRepoImpl.FireStoreRepoImpl
import com.example.todo.domain.repository.firestoreRepo.FireStoreRepo
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseStoreModule {


    @Provides
    @Singleton
    fun provideFirebaseFireStore()=FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFireBaseRepoImpl(
        firebaseFirestore: FirebaseFirestore
    ):FireStoreRepo{
        return FireStoreRepoImpl(firebaseFirestore = firebaseFirestore)
    }


}