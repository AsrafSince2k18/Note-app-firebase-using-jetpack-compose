package com.example.todo.data.repoImpl.auth

import com.example.todo.domain.repository.auth.SignInRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await

class SignInRepoImpl(
    private val firebaseAuth: FirebaseAuth
) :SignInRepo{
    override suspend fun signInUser(
        email: String,
        password: String,
        isSuccess: (Boolean) -> Unit,
        isError: (Exception) -> Unit
    ) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        isSuccess(it.isSuccessful)
                    }else{
                        isError(it.exception!!)
                    }
                }
                .addOnFailureListener {
                    isError(it)
                }
                .await()
        }catch (e:Exception){
            isError(e)
        }catch (e:FirebaseAuthException){
            isError(e)
        }
    }
}