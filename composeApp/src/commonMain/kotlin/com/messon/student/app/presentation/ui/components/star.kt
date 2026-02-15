package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

@Composable
fun Star(
    modifier: Modifier = Modifier,
    filledColor: Color,
    outlineColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .size(35.dp)
            .clip(shape = CircleShape)
            .clickable { onClick() },
        painter = if (isSelected)
            rememberVectorPainter(Icons.Filled.Star)
        else
            rememberVectorPainter(Icons.Outlined.Star),
        contentDescription = "star rating",
        tint = if (isSelected) filledColor else outlineColor,
    )
}


@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    filledColor: Color,
    outlineColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        for (i in 1..maxStars) {
            Star(
                isSelected = i <= rating,
                filledColor =filledColor,
                outlineColor =outlineColor,
                onClick = { onRatingChanged(i) }
            )
        }
    }
}

