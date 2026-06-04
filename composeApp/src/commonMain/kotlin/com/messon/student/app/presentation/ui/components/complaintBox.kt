package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.messon.student.app.utils.MealSlot
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensanssemiBold
import messon.composeapp.generated.resources.qr
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun QuickAccessBox(
    icon: Painter,
    title: String,
    modifier: Modifier,
    onClick: () -> Unit,
    isClickable: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp) // Sleek height footprint
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.surface, // Matches surface palette
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f), // Soft glass outline
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = isClickable) {
                onClick()
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Beautifully tinted round container for the leading icon
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), // Subtle brand color tint
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary, // Brand-accented leading icon
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
            color = MaterialTheme.colorScheme.onSurface, // High legibility title color
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f) // Automatically fills horizontal space
        )

        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "Navigate to feature",
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f), // Soft secondary grey arrow
            modifier = Modifier.size(18.dp)
        )
    }
}

