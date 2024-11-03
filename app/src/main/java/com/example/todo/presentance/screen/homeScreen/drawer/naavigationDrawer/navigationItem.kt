package com.example.todo.presentance.screen.homeScreen.drawer.naavigationDrawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.presentance.screen.homeScreen.drawer.itemState.DrawerItem

@Composable
fun NavigationItem(
    drawerItem: DrawerItem,
    selected : Boolean,
    onClick : () -> Unit
) {

    Surface(
        onClick={onClick()},
        shadowElevation = if(selected)2.dp else 0.dp,
        shape = RoundedCornerShape(12.dp),
        color = if(selected)MaterialTheme.colorScheme.surfaceVariant else Color.Unspecified,
        modifier=Modifier
            .padding(  22.dp)
    ) {

        Row (
            modifier= Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = drawerItem.title,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = if(selected)FontWeight.SemiBold else FontWeight.Normal)

            Icon(imageVector = drawerItem.icon, contentDescription = null,
                tint = if(selected)MaterialTheme.colorScheme.primary else Color.Unspecified)

        }

    }

}

