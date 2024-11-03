package com.example.todo.presentance.screen.writeScreen.addFetures

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Image
import androidx.compose.ui.graphics.vector.ImageVector

sealed class GallerySection(
    val image : ImageVector,
    val content : String
) {

    data object Gallery:GallerySection(
        image = Icons.Outlined.Image,
        content = "Add image"
    )

    data object DateAndTime:GallerySection(
        image = Icons.Outlined.AccessTime,
        content = "Date and time"
    )
}

