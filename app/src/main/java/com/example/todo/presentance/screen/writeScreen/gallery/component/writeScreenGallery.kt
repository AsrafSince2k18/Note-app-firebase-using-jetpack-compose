package com.example.todo.presentance.screen.writeScreen.gallery.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.todo.presentance.screen.writeScreen.gallery.state.GalleryImageHolder
import com.example.todo.presentance.screen.writeScreen.gallery.state.GalleryState

@Composable
fun WriteScreenImage(
    imageSize: Dp = 60.dp,
    spaceBetween: Dp = 12.dp,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    galleryState: GalleryState,
    removeImage: (GalleryImageHolder) -> Unit,
    onClearAll: () -> Unit

) {

    val scrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        val takeImage = remember {
            derivedStateOf { this.maxWidth.div(imageSize + spaceBetween).toInt() }
        }.value

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .width(spaceBetween)
            )

            if(galleryState.galleryState.isNotEmpty()){
                ClearAll(
                    onClearAll = { onClearAll() },
                    imageSize = imageSize,
                    shape = shape
                )

                Spacer(
                    modifier = Modifier
                        .width(spaceBetween)
                )
            }

            galleryState.galleryState.take(takeImage).forEach { image ->
                ImageSurface(
                    image = image.imageUri,
                    shape = shape,
                    size = imageSize,
                    remove = {
                        removeImage(image)
                    }
                )
                Spacer(
                    modifier = Modifier
                        .width(spaceBetween)
                )
            }

        }

    }

}


@Composable
fun ClearAll(
    imageSize: Dp,
    shape: RoundedCornerShape,
    onClearAll: () -> Unit
) {

    Surface(
        modifier = Modifier
            .size(size = imageSize),
        onClick = onClearAll,
        shape = shape,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Clear All",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }

}




@Composable
fun ImageSurface(
    image: Any,
    shape: RoundedCornerShape,
    size: Dp,
    remove: (Any) -> Unit
) {

    Box(
        modifier = Modifier
            .size(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .align(Alignment.TopEnd)
            .size(15.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape
            )
            .clickable { remove(image) }) {
            Icon(
                imageVector = Icons.Outlined.Remove,
                contentDescription = null
            )
        }
        Surface(
            modifier = Modifier
                .size(size),
            shape = shape,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image)
                            .crossfade(true)
                            .crossfade(500)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                }

            }

        }
    }
}

