package com.example.todo.presentance.screen.signUp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.repository.auth.SignUpRepo
import com.example.todo.domain.useCase.auth.signUp.SignUpConformPassword
import com.example.todo.domain.useCase.auth.signUp.SignUpEmail
import com.example.todo.domain.useCase.auth.signUp.SignUpPassword
import com.example.todo.presentance.screen.signUp.stateEvent.SignUpEvent
import com.example.todo.presentance.screen.signUp.stateEvent.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpEmail: SignUpEmail,
    private val signUpPassword: SignUpPassword,
    private val signUpConformPassword: SignUpConformPassword,

    private val signUpRepo: SignUpRepo
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.Email -> {
                _signUpState.update {
                    it.copy(email = event.email)
                }
            }

            is SignUpEvent.Password -> {
                _signUpState.update {
                    it.copy(password = event.password)
                }
            }

            is SignUpEvent.ConformPassword -> {
                _signUpState.update {
                    it.copy(conformPassword = event.cPassword)
                }
            }

            SignUpEvent.SignUpBtn -> {
                signUpBtn()
            }
        }
    }


    private fun signUpBtn() {
        viewModelScope.launch {

            val email = signUpEmail.invoke(signUpState.value.email)
            val password = signUpPassword.invoke(signUpState.value.password)
            val cPassword = signUpConformPassword.invoke(
                signUpState.value.conformPassword,
                signUpState.value.password
            )


            val hasError = listOf(email, password, cPassword).any { !it.success }


            if (hasError) {
                withContext(Dispatchers.Main) {
                    _signUpState.update {
                        it.copy(
                            isEmailError = email.isError,
                            isPasswordError = password.isError,
                            isConformPasswordError = cPassword.isError
                        )
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _signUpState.update {
                        it.copy(
                            isEmailError = null,
                            isPasswordError = null,
                            isConformPasswordError = null,
                            signBtnLoading = true
                        )
                    }
                }

                withContext(Dispatchers.IO) {
                    signUpRepo.createNewUser(
                        email = signUpState.value.email,
                        password = signUpState.value.conformPassword,
                        isSuccess = { valid ->
                            viewModelScope.launch(Dispatchers.Main) {
                                _signUpState.update {
                                    it.copy(
                                        signBtnLoading = false,
                                        userValid = valid
                                    )
                                }
                            }
                        },
                        isError = { error ->
                            viewModelScope.launch(Dispatchers.Main) {
                                _signUpState.update {
                                    it.copy(
                                        signBtnLoading = false,
                                        firebaseError = error.message
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