package com.messon.student.app.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import org.jetbrains.compose.resources.Font

@Composable
fun PrefSelectionButton(
    isClickable: Boolean,
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    activeColor: Color,
    activeBgColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(if (isSelected) activeColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), label = "border")
    val bgColor by animateColorAsState(if (isSelected) activeBgColor.copy(alpha = 0.5f) else Color.White, label = "bg")
    val iconBgColor by animateColorAsState(if (isSelected) activeBgColor else MaterialTheme.colorScheme.background, label = "iconBg")
    val contentColor by animateColorAsState(if (isSelected) activeColor else MaterialTheme.colorScheme.outline, label = "content")

    Column(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(16.dp))
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(isClickable) { onClick() }
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(iconBgColor, CircleShape)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold) ),
            color = if (isSelected) activeColor.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
    }
}
