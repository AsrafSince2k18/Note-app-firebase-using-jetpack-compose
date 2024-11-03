package com.example.todo.presentance.screen.signIn.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.ui.theme.TODOTheme

@Composable
fun GBtn(
    isLoading: Boolean,
    primaryText: String = "Continue with Google",
    secondaryText: String = "Please wait...",
    onClick: () -> Unit
) {

    var btnText by remember { mutableStateOf(primaryText) }

    LaunchedEffect(key1 = isLoading) {
        btnText = if (isLoading) secondaryText else primaryText
    }

    Surface(
        onClick = onClick,
        shadowElevation = 3.dp,
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.google),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
            )


            Spacer(
                modifier = Modifier
                    .width(16.dp)
            )


            Text(
                text = btnText,
                fontFamily = FontFamily.Serif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            if (isLoading) {
                Spacer(
                    modifier = Modifier
                        .width(12.dp)
                )
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(22.dp)
                )
            }

        }
    }

}

@Preview
@Composable
private fun GPr() {
    TODOTheme { GBtn(isLoading = true) { } }
}