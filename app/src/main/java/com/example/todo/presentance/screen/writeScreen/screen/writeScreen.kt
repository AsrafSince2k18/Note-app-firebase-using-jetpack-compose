package com.example.todo.presentance.screen.writeScreen.screen

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.presentance.screen.writeScreen.addFetures.DeleteSection
import com.example.todo.presentance.screen.writeScreen.addFetures.GallerySection
import com.example.todo.presentance.screen.writeScreen.component.ColorSelect
import com.example.todo.presentance.screen.writeScreen.component.WriteBottomBar
import com.example.todo.presentance.screen.writeScreen.component.WriteTopBar
import com.example.todo.presentance.screen.writeScreen.gallery.component.WriteScreenImage
import com.example.todo.presentance.screen.writeScreen.stateEvent.WriteEvent
import com.example.todo.presentance.screen.writeScreen.stateEvent.WriteState
import com.example.todo.presentance.utils.colorsItem
import com.example.todo.presentance.utils.dateAndTimeFormat
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    writeState: WriteState,
    writeEvent: (WriteEvent) -> Unit,
    onBack: () -> Unit
) {

    val keyBoardManager = LocalFocusManager.current
    val keyBoardControl = remember { FocusRequester() }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val horizontalScroll = rememberScrollState()
    val verticalScroll = rememberScrollState()

    // TODO: DATE AND TIME SHEET
    val clockSheet = rememberUseCaseState()
    val dateSheet = rememberUseCaseState()

    // TODO: BOTTOM_SHEET
    val galleryBottomSheet = rememberModalBottomSheetState()

    val colorBottomSheet = rememberModalBottomSheetState()

    val deleteBottomSheet = rememberModalBottomSheetState()


    var localTime by remember { mutableStateOf(LocalTime.now()) }
    var localDate by remember { mutableStateOf(LocalDate.now()) }


    val timeFormat = remember(key1 = localTime) {
        DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
            .format(localTime)
    }

    val dateFormat = remember(key1 = localDate) {
        DateTimeFormatter.ofPattern("EEEE,dd MMMM yyyy", Locale.getDefault())
            .format(localDate)
    }

    var pinned by remember { mutableStateOf(false) }

    val gallerySectionList = listOf(
        GallerySection.Gallery,
        GallerySection.DateAndTime
    )

    val deleteSectionList = listOf(
        DeleteSection.Delete
    )


    LaunchedEffect(key1 = Unit) {
        keyBoardControl.requestFocus()
    }

    ClockDialog(
        state = clockSheet,
        selection = ClockSelection.HoursMinutes { hr, min ->
            localTime = LocalTime.of(hr, min)
            dateSheet.show()
        },
        config = ClockConfig(
            defaultTime = LocalTime.now(),
            is24HourFormat = false
        )
    )

    CalendarDialog(
        state = dateSheet,
        selection = CalendarSelection.Date { date ->
            localDate = date
            val calender = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, localTime.hour)
                set(Calendar.MINUTE, localTime.minute)
                set(Calendar.DAY_OF_MONTH, date.dayOfMonth)
                set(Calendar.YEAR, date.year)
            }.timeInMillis
            writeEvent(WriteEvent.TimeDate(calender))
        },
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            cameraDate = LocalDate.now(),
            style = CalendarStyle.WEEK
        ),
    )


    val visualMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uri ->
        uri.forEach {
            val imageType = context.contentResolver.getType(it)?.split("/")?.last() ?: "jpg"
            writeEvent(WriteEvent.UriAndImageType(uri = it, imageType = imageType))

        }
    }




    if (galleryBottomSheet.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    galleryBottomSheet.hide()
                }
            },
            sheetState = galleryBottomSheet,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
        {
            gallerySectionList.forEachIndexed { index, gallery ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when (index) {
                                    0 -> {
                                        visualMedia.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    }

                                    1 -> {

                                        clockSheet.show()
                                    }

                                    else -> throw IllegalArgumentException("Out of index")
                                }
                            }
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(imageVector = gallery.image, contentDescription = null)
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        Text(
                            gallery.content,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            }
        }
    }

    if (colorBottomSheet.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    colorBottomSheet.hide()
                }
            },
            sheetState = colorBottomSheet,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface,
            scrimColor = Color.Unspecified

        ) {

            Text(
                text = "Colour",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 12.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(horizontalScroll)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                colorsItem.forEach { color ->
                    Spacer(
                        modifier = Modifier
                            .width(4.dp)
                    )
                    ColorSelect(color, onClick = {
                        writeEvent(WriteEvent.ColorChange(it))
                    })

                    Spacer(
                        modifier = Modifier
                            .width(4.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

        }
    }


    if (deleteBottomSheet.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    deleteBottomSheet.hide()
                }
            },
            sheetState = deleteBottomSheet,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
        {
            deleteSectionList.forEachIndexed { index, delete ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .fillMaxWidth()
                            .clickable {
                                when (index) {
                                    0 -> {
                                        writeEvent(WriteEvent.DeleteBtn)
                                        onBack()
                                    }

                                    else -> throw IllegalArgumentException("Out of index")
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(imageVector = delete.icon, contentDescription = null)
                        Spacer(
                            modifier = Modifier
                                .width(8.dp)
                        )
                        Text(
                            delete.content,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                }
            }
        }
    }




    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize(),
        topBar = {
            WriteTopBar(
                pinSelected = pinned,
                onBack = { onBack() },
                onPin = {
                    pinned = !pinned
                },
                onSave = {
                    writeEvent(WriteEvent.SaveBtn)
                    onBack()
                }
            )
        },
        bottomBar = {
            WriteBottomBar(
                noteData = writeState.noteData,
                onGallery = {
                    coroutineScope.launch {
                        galleryBottomSheet.expand()
                    }
                },
                onColor = {
                    coroutineScope.launch {
                        colorBottomSheet.expand()
                    }
                },
                imageUpload = {

                },
                onDelete = {
                    coroutineScope.launch {
                        deleteBottomSheet.expand()
                    }
                }

            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(8f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                ) {
                    TextField(
                        value = writeState.title,
                        onValueChange = {
                            writeEvent(WriteEvent.Title(it))
                        },
                        placeholder = {
                            Text(
                                text = "Title",
                                color = MaterialTheme.colorScheme.onSurface.copy(0.32f),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 22.sp
                            )
                        },
                        keyboardActions = KeyboardActions(
                            onNext = {
                                keyBoardManager.moveFocus(FocusDirection.Next)
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(keyBoardControl)
                    )

                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                    )


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = if (writeState.noteData != null) dateAndTimeFormat(writeState.timeDate) else "$dateFormat $timeFormat",
                            textAlign = TextAlign.Start,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(writeState.color)
                            )

                            TextField(
                                value = writeState.subTitle,
                                onValueChange = {
                                    writeEvent(WriteEvent.SubTitle(it))
                                },
                                placeholder = {
                                    Text(
                                        text = "Note Subtitle",
                                        color = MaterialTheme.colorScheme.onSurface.copy(0.32f)
                                    )
                                },
                                singleLine = true,
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        keyBoardManager.moveFocus(FocusDirection.Next)
                                    }
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Text
                                ),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                        }
                    }


                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                    )

                    TextField(
                        value = writeState.content,
                        onValueChange = {
                            writeEvent(WriteEvent.Content(it))
                        },
                        placeholder = {
                            Text(
                                text = "Content",
                                color = MaterialTheme.colorScheme.onSurface.copy(0.32f),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily.SansSerif
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyBoardManager.clearFocus(true)
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Default,
                            keyboardType = KeyboardType.Text
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .verticalScroll(verticalScroll)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                WriteScreenImage(
                    galleryState = writeState.galleryState,
                    removeImage = { remove ->
                        writeState.galleryState.deleteImage(remove)
                    },
                    onClearAll = {
                        writeState.galleryState.clearAllImage()
                    }
                )

            }

        }
    }

}