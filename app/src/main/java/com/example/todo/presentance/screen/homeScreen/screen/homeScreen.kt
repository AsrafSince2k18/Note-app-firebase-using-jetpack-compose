package com.example.todo.presentance.screen.homeScreen.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.presentance.screen.homeScreen.component.HomeTopBar
import com.example.todo.presentance.screen.homeScreen.component.NoteContent
import com.example.todo.presentance.screen.homeScreen.drawer.itemState.DrawerItem
import com.example.todo.presentance.screen.homeScreen.drawer.itemState.DrawerState
import com.example.todo.presentance.screen.homeScreen.drawer.itemState.toOpen
import com.example.todo.presentance.screen.homeScreen.drawer.naavigationDrawer.NavigationCustomDrawer
import com.example.todo.presentance.screen.homeScreen.stateEvent.HomeEvent
import com.example.todo.presentance.screen.homeScreen.stateEvent.HomeState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import kotlin.math.roundToInt


@Composable
fun MainScreen(
    homeState: HomeState,
    homeEvent: (HomeEvent) -> Unit,
    onSignIn : () -> Unit,
    onWriteScreen : () -> Unit,
    onWriteScreenId : (String) -> Unit,
) {

    var logOut by remember { mutableStateOf(false) }
    var clearAll by remember { mutableStateOf(false) }

    var drawerItem by remember { mutableStateOf(DrawerItem.ClearAll) }
    var drawerState by remember { mutableStateOf(DrawerState.Closed) }


    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }

    val offsetValue by remember { derivedStateOf { (screenWidth.value/2.5).dp } }


    val animiateOffset by animateDpAsState(
        targetValue = if(drawerState.toOpen()) offsetValue else 0.dp, label = ""
    )

    val animateScale by animateFloatAsState(if(drawerState.toOpen())0.9f else 1f, label = "")



    BackHandler(
        enabled = drawerState.toOpen(),
        onBack = {
            drawerState=DrawerState.Closed
        }
    )

    if(logOut){
        AlertDialog(
            onDismissRequest = {
                logOut=false
            },
            dismissButton = {
                OutlinedButton(onClick = {logOut=false}) {
                    Text(text = "No")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val user = Firebase.auth
                    user.signOut()
                    onSignIn()
                }) {
                    Text(text = "Yes")
                }
            },
            title = {
                Text(text = "Log out",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
            },
            shape = RoundedCornerShape(22.dp),
            text = {
                Text(text = "Are you sure want to logout?")
            }
        )
    }


    if(clearAll){
        AlertDialog(
            onDismissRequest = {
                clearAll=false
            },
            dismissButton = {
                OutlinedButton(onClick = {clearAll=false}) {
                    Text(text = "No")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                        homeEvent(HomeEvent.ClearAllData)
                }) {
                    Text(text = "Yes")
                }
            },
            title = {
                Text(text = "Clear all",
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
            },
            shape = RoundedCornerShape(22.dp),
            text = {
                Text(text = "Are you sure want to clear all notes?")
            }
        )
    }

    Box(modifier= Modifier
        .background(MaterialTheme.colorScheme.surface)
        .background(MaterialTheme.colorScheme.onSurface.copy(0.05f))
        .systemBarsPadding()
        .fillMaxSize()
    ){

        NavigationCustomDrawer(
            drawerItem = drawerItem,
            onDrawerItem = {
                drawerItem=it
            },
            onClose = {
                drawerState=DrawerState.Closed
            },
            onClearAll = {
                clearAll=!clearAll
            },
            onLogOut = {
               logOut=!logOut
            }
        )

        HomeScreen(
            modifier = Modifier
                .systemBarsPadding()
                .scale(scale = animateScale)
                .offset(x = animiateOffset, y = 0.dp)
                .clip(
                    if (drawerState == DrawerState.Opened) RoundedCornerShape(12.dp) else RoundedCornerShape(
                        0.dp
                    )
                ),
            drawerState = drawerState,
            onDrawerState = {
                drawerState=it
            },
            onWriteScreen = {
                onWriteScreen()
            },
            onWriteScreenId = {
                onWriteScreenId(it)
            },
            homeState = homeState,
            homeEvent = homeEvent
        )

    }


}

@Composable
fun HomeScreen(
    modifier: Modifier=Modifier,
    homeState: HomeState,
    homeEvent: (HomeEvent) -> Unit,
    drawerState: DrawerState,
    onDrawerState: (DrawerState) -> Unit,
    onWriteScreenId : (String) -> Unit,
    onWriteScreen : () -> Unit,
) {


    var orderItem by remember { mutableStateOf(false) }

    val currentUser = com.google.firebase.ktx.Firebase.auth.currentUser


    Scaffold(
        modifier=modifier
            .clickable(enabled = drawerState==DrawerState.Opened){
              onDrawerState(DrawerState.Closed)
            },
        topBar = {
            HomeTopBar(
                search = homeState.search,
                onSearch = {
                    homeEvent(HomeEvent.Search(it))
                },
                onSearchBtn = {
                    homeEvent(HomeEvent.SearchBtn)
                },
                userProfile = currentUser?.photoUrl,
                currentUser = currentUser!=null,
                orderItem = orderItem,
                onOrderItem = {
                    orderItem=!orderItem
                },
                onDrawer ={
                   onDrawerState(it)
                },
                drawerState = drawerState
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {onWriteScreen()}) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize(),
            contentAlignment = Alignment.Center){

            if(homeState.noteItemList.isEmpty()){
                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center){
                        Text(text = "Add note",
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.Serif,
                                fontSize = 22.sp
                            ))
                }
            }else {

                if (homeState.search.isBlank()) {
                    if (orderItem) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(homeState.noteItemList) { noteData ->
                                NoteContent(noteData = noteData,
                                    onPassData = {
                                        onWriteScreenId(it)
                                    })
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(6.dp),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(homeState.noteItemList) { noteData ->
                                NoteContent(noteData = noteData,
                                    onPassData = {
                                        onWriteScreenId(it)
                                    })
                            }
                        }
                    }
                } else {
                    if (orderItem) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(homeState.searchItemList) { noteData ->
                                NoteContent(noteData = noteData,
                                    onPassData = {
                                        onWriteScreenId(it)
                                    })
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(6.dp),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(homeState.searchItemList) { noteData ->
                                NoteContent(noteData = noteData,
                                    onPassData = {
                                        onWriteScreenId(it)
                                    })
                            }
                        }
                    }
                }
            }

        }

    }

}