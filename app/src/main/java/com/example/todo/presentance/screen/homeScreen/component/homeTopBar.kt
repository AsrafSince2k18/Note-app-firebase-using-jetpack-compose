package com.example.todo.presentance.screen.homeScreen.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.todo.R
import com.example.todo.presentance.screen.homeScreen.drawer.itemState.DrawerState
import com.example.todo.presentance.screen.homeScreen.drawer.itemState.toOpposite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    search:String,
    onSearch : (String) -> Unit,
    onSearchBtn : ()->Unit,
    userProfile: Uri?,
    currentUser : Boolean,
    orderItem :Boolean,
    onOrderItem : (Boolean) -> Unit,
    drawerState: DrawerState,
    onDrawer : (DrawerState) -> Unit
) {

    val asyncImage = rememberAsyncImagePainter(
        model= ImageRequest.Builder(LocalContext.current)
            .data(userProfile)
            .size(25)
            .crossfade(true)
            .crossfade(400)
            .build()).state


    TopAppBar(
        title = {
            TextField(value = search,
                onValueChange = {
                    onSearch(it)
                },
                placeholder = {
                    Text(text = "Search your notes")
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchBtn()
                    }
                ),
                textStyle = TextStyle(
                    fontSize = 16.sp
                )
            )
        },
        windowInsets = WindowInsets(top = 12.dp, bottom = 12.dp, left = 12.dp, right = 12.dp),
        navigationIcon = {
            IconButton(onClick = {
                onDrawer(drawerState.toOpposite())
            }) {
                Icon(imageVector = Icons.Rounded.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface)
            }
        },
        actions = {
            IconButton(onClick = {
                onOrderItem(orderItem)
            }) {
                Icon(painter = if(orderItem) painterResource(R.drawable.list) else painterResource(R.drawable.grid),
                    contentDescription = null)
            }

                if(currentUser) {
                    when(asyncImage){
                        is AsyncImagePainter.State.Loading -> {
                            Icon(
                                painter = painterResource(R.drawable.user),
                                tint = Color.Unspecified,
                                contentDescription = null
                            )
                        }
                        is AsyncImagePainter.State.Success -> {
                            Image(painter = asyncImage.painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape))
                        }
                        else ->{
                            throw IllegalArgumentException("user profile not found")
                        }
                    }
                }else{
                    Icon(
                        painter = painterResource(R.drawable.user),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }


        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier
            .systemBarsPadding()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp)),


        )

}

