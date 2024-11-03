package com.example.todo.presentance.screen.signIn.viewModel

import android.util.Log
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.repository.auth.SignInRepo
import com.example.todo.domain.useCase.auth.signIn.SignInEmail
import com.example.todo.domain.useCase.auth.signIn.SignInPassword
import com.example.todo.presentance.screen.signIn.stateEvent.SignInEvent
import com.example.todo.presentance.screen.signIn.stateEvent.SignInState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInEmail: SignInEmail,
    private val signInPassword: SignInPassword,
    private val signInRepo: SignInRepo
) : ViewModel() {

    private val _signInState = MutableStateFlow(SignInState())

    val signInState = _signInState.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.Email -> {
                _signInState.update {
                    it.copy(email = event.email)
                }
            }

            is SignInEvent.Password -> {
                _signInState.update {
                    it.copy(password = event.password)
                }
            }

            SignInEvent.SignInBtn -> {
                signInBtn()
            }

            SignInEvent.SignInGBtn -> {

            }

            is SignInEvent.GoogleAuth -> {
                viewModelScope.launch {
                    _signInState.update {
                        it.copy(gBtnLoading = true)
                    }
                    FirebaseAuth.getInstance()
                        .signInWithCredential(event.authCredential)
                        .addOnCompleteListener { result ->
                            if (result.isSuccessful) {
                                _signInState.update {
                                    it.copy(
                                        userValid = result.isSuccessful,
                                        gBtnLoading = false
                                    )
                                }
                            } else {
                                _signInState.update {
                                    it.copy(
                                        userValid = false,
                                        gBtnLoading = false,
                                        firebaseError = result.exception?.message
                                    )
                                }
                            }
                        }
                        .addOnFailureListener { error ->
                            _signInState.update {
                                it.copy(
                                    gBtnLoading = false,
                                    firebaseError = error.message
                                )
                            }
                        }

                }
            }
        }
    }

    private fun signInBtn() {
        viewModelScope.launch {
            val email = signInEmail.invoke(signInState.value.email)
            val password = signInPassword.invoke(signInState.value.password)


            val hasError = listOf(email, password)
                .any { !it.success }

            if (hasError) {
                withContext(Dispatchers.Main) {
                    _signInState.update {
                        it.copy(
                            isEmailError = email.isError,
                            isPasswordError = password.isError
                        )
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _signInState.update {
                        it.copy(
                            isEmailError = null,
                            isPasswordError = null,
                            signInBtnLoading = true
                        )
                    }
                }

                withContext(Dispatchers.IO) {
                    signInRepo.signInUser(
                        email = signInState.value.email,
                        password = signInState.value.password,
                        isSuccess = { valid ->
                            viewModelScope.launch(Dispatchers.Main) {
                                _signInState.update {
                                    it.copy(
                                        userValid = valid,
                                        signInBtnLoading = false
                                    )
                                }
                            }
                        },
                        isError = { error ->
                            viewModelScope.launch(Dispatchers.Main) {
                                _signInState.update {
                                    it.copy(
                                        firebaseError = error.message,
                                        signInBtnLoading = false
                                    )
                                }
                            }
                        }
                    )
                }
            }

        }
    }

}