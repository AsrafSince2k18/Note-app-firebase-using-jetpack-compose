package com.example.todo.presentance.screen.signUp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.example.todo.R
import com.example.todo.presentance.screen.signUp.stateEvent.SignUpEvent
import com.example.todo.presentance.screen.signUp.stateEvent.SignUpState
import com.example.todo.ui.theme.topColor

@Composable
fun SignUpScreen(
signUpState: SignUpState,
signUpEvent: (SignUpEvent) -> Unit,
onNavHome : () -> Unit
) {

    val focusManger = LocalFocusManager.current
    val keyBoardControl = remember {
        FocusRequester()
    }
    val scrollState = rememberScrollState()

    var passwordVisual by remember { mutableStateOf(false) }
    var conformVisual by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        keyBoardControl.requestFocus()
    }

    LaunchedEffect(key1 = signUpState.userValid) {
        if(signUpState.userValid) onNavHome()
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
                    text = "Create an",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 55.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Account",
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
            Column{
                OutlinedTextField(
                    value = signUpState.email,
                    onValueChange = {
                        signUpEvent(SignUpEvent.Email(it))
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
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    isError = signUpState.isEmailError!=null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(keyBoardControl)
                )

                signUpState.isEmailError?.let {
                    Text(text = it,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp)
                }

                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )

                OutlinedTextField(
                    value = signUpState.password,
                    onValueChange = {
                        signUpEvent(SignUpEvent.Password(it))
                    },
                    label = {
                        Text(text = "Password")
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
                    isError = signUpState.isPasswordError!=null,
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if(passwordVisual)VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisual =!passwordVisual
                        }) {
                            if(passwordVisual){
                                Icon(
                                    painter = painterResource(R.drawable.see),
                                    tint = MaterialTheme.colorScheme.secondary,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(22.dp)
                                )

                            }else{
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

                signUpState.isPasswordError?.let {
                    Text(text = it,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp)
                }

                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )

                OutlinedTextField(
                    value = signUpState.conformPassword,
                    onValueChange = {
                        signUpEvent(SignUpEvent.ConformPassword(it))
                    },
                    label = {
                        Text(text = "Conform password")
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
                    shape = RoundedCornerShape(8.dp),
                    visualTransformation = if(conformVisual)VisualTransformation.None else PasswordVisualTransformation(),
                    isError = signUpState.isConformPasswordError!=null,
                    trailingIcon = {
                        IconButton(onClick = {
                            conformVisual =!conformVisual
                        }) {
                            if(conformVisual){
                                Icon(
                                    painter = painterResource(R.drawable.see),
                                    tint = MaterialTheme.colorScheme.secondary,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(22.dp)
                                )

                            }else{
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

                signUpState.isConformPasswordError?.let {
                    Text(text = it,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp)
                }


                signUpState.firebaseError?.let {
                    Text(text = it,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp)
                }

            }

            Spacer(
                modifier = Modifier
                    .height(28.dp)
            )

            Column {
                ElevatedButton(
                    onClick = {
                        signUpEvent(SignUpEvent.SignUpBtn)
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
                    Text(text = "Sign up",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif)

                    if(signUpState.signBtnLoading){
                        Spacer(modifier=Modifier
                            .width(8.dp))
                        CircularProgressIndicator(modifier=Modifier
                            .size(22.dp))
                    }

                }

            }

        }

    }

}
