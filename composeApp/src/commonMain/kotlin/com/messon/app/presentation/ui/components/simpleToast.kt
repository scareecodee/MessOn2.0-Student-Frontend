package com.messon.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SimpleToast(
    message: String,
    visible: Boolean=true,
    durationMillis: Long = 2000,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    textColor: Color = Color.White,
    gravity: Alignment = Alignment.BottomCenter,
    onDismiss: () -> Unit = {},
    fontSize:Int,
    font: Font

) {

    if (visible) {
        LaunchedEffect(message, visible) {
            delay(durationMillis)
            onDismiss()
        }
    }

    if (!visible) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = gravity
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(14.dp))
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                fontFamily = FontFamily(font),
                maxLines = 1,
                fontSize = fontSize.sp,
                text = message,
                color = textColor
            )
        }
    }
}
