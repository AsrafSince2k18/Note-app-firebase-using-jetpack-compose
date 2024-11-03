package com.example.todo.presentance.screen.writeScreen.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.PushPin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTopBar(
    pinSelected : Boolean,
    onBack : () -> Unit,
    onPin : () -> Unit,
    onSave : () -> Unit
) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {onPin()}) {
                Icon(imageVector = if(pinSelected)Icons.Filled.PushPin else Icons.Outlined.PushPin, contentDescription = null)
            }
            IconButton(onClick = onSave) {
                Icon(imageVector = Icons.Outlined.SaveAlt, contentDescription = null)
            }
        },

    )

}