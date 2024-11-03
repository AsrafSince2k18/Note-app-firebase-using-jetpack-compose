package com.example.todo.presentance.screen.homeScreen.drawer.naavigationDrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.presentance.screen.homeScreen.drawer.itemState.DrawerItem
import com.example.todo.ui.theme.TODOTheme

@Composable
fun NavigationCustomDrawer(
    drawerItem: DrawerItem,
    onDrawerItem : (DrawerItem) -> Unit,
    onLogOut : () -> Unit,
    onClearAll : () -> Unit,
    onClose: () -> Unit
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .background(MaterialTheme.colorScheme.surface.copy(0.32f))
            .fillMaxWidth(0.6f)
            .fillMaxHeight()
    ) {

        Column (
            modifier = Modifier
                .padding(top = 52.dp)
        ) {
            IconButton(onClick = onClose,
                modifier=Modifier
                    .padding(horizontal = 6.dp)
                ) {
                Icon(imageVector = Icons.Rounded.Close,
                    contentDescription = null)
            }
            Column(
                modifier = Modifier
                    .padding(52.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TODO",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp
                )

                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )

                Text(
                    text = "Write Notes",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }

            Column {

                DrawerItem.entries.toTypedArray().take(1).forEach {drawer->

                    NavigationItem(
                        drawerItem = drawer,
                        selected = drawer==drawerItem,
                        onClick = {
                           when(drawer){
                               DrawerItem.ClearAll -> {
                                   onDrawerItem(drawer)
                                   onClearAll()
                               }
                               else ->{
                                   throw IllegalArgumentException("Not found")
                               }
                           }
                        }
                    )

                }

                Spacer(modifier=Modifier
                    .weight(1f))

                DrawerItem.entries.toTypedArray().takeLast(1).forEach {drawer->

                    NavigationItem(
                        drawerItem = drawer,
                        selected = drawer==drawerItem,
                        onClick = {
                            when(drawer){
                                DrawerItem.LogOut -> {
                                    onDrawerItem(drawer)
                                    onLogOut()
                                }
                                else -> throw IllegalArgumentException("not found")
                            }
                        }
                    )

                }


            }

        }
    }
}
