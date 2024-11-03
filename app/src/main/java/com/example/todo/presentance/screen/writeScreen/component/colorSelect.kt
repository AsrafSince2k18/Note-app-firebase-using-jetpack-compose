package com.example.todo.presentance.screen.writeScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TODOTheme

@Composable
fun ColorSelect(
    color:Color,
    onClick : (Color) -> Unit
) {
    Box(modifier= Modifier
        .size(50.dp)
        .clip(CircleShape)
        .border(width = 4.dp, color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ){
        Box(modifier= Modifier
            .size(35.dp)
            .clip(CircleShape)
            .background(color = color, shape = CircleShape)
            .clickable { onClick(color) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AQW() {
    TODOTheme {
            Box(modifier= Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(width = 4.dp, Color.Blue, shape = CircleShape),
                contentAlignment = Alignment.Center
            ){
                Box(modifier= Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(color = Color.Blue, shape = CircleShape)
                    .clickable {  }
                )
            }


    }
}