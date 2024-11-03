package com.example.todo.presentance.screen.homeScreen.drawer.itemState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val title:String,
    val icon : ImageVector
) {


    ClearAll(
        title = "Clear all",
        icon = Icons.Outlined.ClearAll
    ),
     LogOut(
        title = "Log out",
        icon = Icons.AutoMirrored.Outlined.Logout
    )

}