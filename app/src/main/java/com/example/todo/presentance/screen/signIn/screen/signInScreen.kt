package com.example.todo.presentance.screen.signIn.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.todo.R
import com.example.todo.presentance.screen.signIn.component.GBtn
import com.example.todo.presentance.screen.signIn.stateEvent.SignInEvent
import com.example.todo.presentance.screen.signIn.stateEvent.SignInState
import com.example.todo.presentance.utils.Utils.G_TOKEN
import com.example.todo.ui.theme.topColor
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    signInState: SignInState,
    signInEvent: (SignInEvent) -> Unit,
    onNavSignUp: () -> Unit,
    onNavHome: () -> Unit
) {

    val focusManger = LocalFocusManager.current
    val keyBoardControl = remember {
        FocusRequester()
    }
    val scrollState = rememberScrollState()

    var passwordVisual by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = signInState.userValid) {
        if (signInState.userValid) onNavHome()
    }

    LaunchedEffect(key1 = Unit) {
        keyBoardControl.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {

        Box(
            modifier = Modifier
                .size(150.dp)
                .absoluteOffset(x = (-20).dp, y = (-12).dp)
                .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 122.dp))
                .background(
                    topColor.copy(0.76f),
                    shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 122.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .padding(top = 150.dp)
                .imePadding()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    text = "Hi!",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 55.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Welcome",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 55.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Spacer(
                modifier = Modifier
                    .height(22.dp)
            )
            Column {
                OutlinedTextField(
                    value = signInState.email,
                    onValueChange = {
                        signInEvent(SignInEvent.Email(it))
                    },
                    label = {
                        Text(text = "Email")
                    },
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManger.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    isError = signInState.isEmailError != null,
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(keyBoardControl)
                )

                signInState.isEmailError?.let {
                    Text(
                        text = it,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }


                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )

                OutlinedTextField(
                    value = signInState.password,
                    onValueChange = {
                        signInEvent(SignInEvent.Password(it))
                    },
                    label = {
                        Text(text = "Password")
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManger.clearFocus(true)
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    isError = signInState.isPasswordError != null,
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if (passwordVisual) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisual = !passwordVisual
                        }) {
                            if (passwordVisual) {
                                Icon(
                                    painter = painterResource(R.drawable.see),
                                    tint = MaterialTheme.colorScheme.secondary,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(22.dp)
                                )

                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.close),
                                    tint = MaterialTheme.colorScheme.secondary,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(22.dp)
                                )
                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                signInState.isPasswordError?.let {
                    Text(
                        text = it,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                signInState.firebaseError?.let {
                    Text(
                        text = it,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

            }

            Spacer(
                modifier = Modifier
                    .height(28.dp)
            )

            Column {
                ElevatedButton(
                    onClick = {
                        signInEvent(SignInEvent.SignInBtn)
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 3.dp,
                        pressedElevation = 5.dp
                    ),
                    shape = RoundedCornerShape(22.dp),
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Sign in",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif
                    )

                    if (signInState.signInBtnLoading) {
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(22.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = "Or",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                GBtn(isLoading = signInState.gBtnLoading,
                    onClick = {

                        val googleIdOption = GetGoogleIdOption.Builder()
                            .setServerClientId(G_TOKEN)
                            .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts = false)
                            .build()

                        val getCredential = GetCredentialRequest.Builder()
                            .addCredentialOption(googleIdOption)
                            .build()

                        coroutineScope.launch {
                            try {

                                val credentialManager = CredentialManager.create(context)
                                    .getCredential(context, getCredential)
                                val credential = credentialManager.credential

                                val googleIdToken =
                                    GoogleIdTokenCredential.createFrom(credential.data)

                                val authProvider =
                                    GoogleAuthProvider.getCredential(googleIdToken.idToken, null)
                                signInEvent(SignInEvent.GoogleAuth(authProvider))

                            } catch (e: Exception) {
                                Log.d("e1", "SignInScreen: ${e.message}")
                            } catch (e: GetCredentialCancellationException) {
                                Log.d("e1", "SignInScreen: ${e.message}")
                            }
                        }


                    })

                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account? ",
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Sign up",
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable {
                                onNavSignUp()
                            }
                    )
                }

            }

        }

    }

}


