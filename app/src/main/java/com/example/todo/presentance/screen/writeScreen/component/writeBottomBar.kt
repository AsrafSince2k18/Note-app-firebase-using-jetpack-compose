package com.example.todo.presentance.screen.writeScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.data.local.todo.NoteData

@Composable
fun WriteBottomBar(
    noteData: NoteData?,
    onGallery: () -> Unit,
    onColor: () -> Unit,
    imageUpload : () -> Unit,
    onDelete: () -> Unit,
) {


    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(),
        tonalElevation = 3.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ){
        Row(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {
                    onGallery()
                }) {
                    Icon(imageVector = Icons.Outlined.AddCircleOutline, contentDescription = null)
                }

                IconButton(onClick = {
                    onColor()
                }) {
                    Icon(imageVector = Icons.Outlined.ColorLens, contentDescription = null)
                }
            }


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    imageUpload()
                }) {
                    Icon(imageVector = Icons.Outlined.FileUpload, contentDescription = null)
                }

               if(noteData!=null){
                   IconButton(onClick = {
                       onDelete()
                   }) {
                       Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = null)
                   }
               }
            }
        }
    }

}
