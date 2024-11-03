package com.example.todo.presentance.screen.writeScreen.addFetures

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DeleteSection(
    val icon : ImageVector,
    val content : String
) {

    data object Delete : DeleteSection(
        icon = Icons.Outlined.Delete,
        content = "Delete"
    )

}