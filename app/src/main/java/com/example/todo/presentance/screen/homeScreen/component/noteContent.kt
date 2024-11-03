package com.example.todo.presentance.screen.homeScreen.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.todo.data.local.todo.NoteData
import com.example.todo.presentance.utils.dateAndTimeFormat

@SuppressLint("NewApi")
@Composable
fun NoteContent(
    noteData: NoteData,
    onPassData : (String) -> Unit
) {

    Surface(
        color = Color(noteData.color),
        contentColor = MaterialTheme.colorScheme.onSurface,
        onClick = {onPassData(noteData.id!!)},
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 3.dp,
        tonalElevation = 4.dp,
        modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Column (
            modifier=Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            if (noteData.imageList.isNotEmpty()) {
                noteData.imageList.forEach { image ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image)
                            .crossfade(true)
                            .crossfade(400)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                )
            }

            Text(
                text = noteData.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
            )

            Text(
                text = noteData.subTitle,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )


            Text(
                text = dateAndTimeFormat(noteData.dateTime),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif
                ),
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier=Modifier
                    .fillMaxWidth()
            )
        }
    }

}


