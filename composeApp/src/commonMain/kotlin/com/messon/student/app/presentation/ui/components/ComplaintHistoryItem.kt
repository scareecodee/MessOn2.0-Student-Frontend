package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.app.domain.dataModels.ComplaintHistory
import com.messon.student.app.theme.MessonTheme
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun HistoryItem(item: ComplaintHistory) {
    val isClosed = item.status == "Closed"

    // Status Badge Background
    val statusBg = if (isClosed) {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f) // Subtle neutral backing for closed complaints
    } else {
        MessonTheme.colors.warningContainer // Soft amber warning container for ongoing items
    }

    // Status Badge Text
    val statusTextColor = if (isClosed) {
        MaterialTheme.colorScheme.onSurfaceVariant // Readable muted text
    } else {
        MaterialTheme.colorScheme.secondary // Bold amber secondary text
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        // Dynamic surface background changes seamlessly based on the system theme
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)), // Modern subtle border layout
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.date,
                    // Uses high-legibility secondary text color
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(statusBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item.status,
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                        color = statusTextColor
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = item.type,
                color = MaterialTheme.colorScheme.onSurface, // Proper dynamic text nesting color
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = item.text,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Dynamic grey mapping ensures readability in dark screens
                lineHeight = 18.sp
            )
        }
    }
}