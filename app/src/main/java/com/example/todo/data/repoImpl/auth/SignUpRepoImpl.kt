package com.example.todo.data.repoImpl.auth

import com.example.todo.domain.repository.auth.SignUpRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpRepoImpl(
    private val firebaseAuth: FirebaseAuth
) : SignUpRepo{

    override suspend fun createNewUser(
        email:String,
        password:String,
        isSuccess: (Boolean) -> Unit,
        isError: (Exception) -> Unit
    ) {
        try {
            withContext(Dispatchers.IO) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
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
            }

        }catch (e:Exception){
            isError(e)
        }
        catch (e:FirebaseAuthException){
            isError(e)
        }
    }

}