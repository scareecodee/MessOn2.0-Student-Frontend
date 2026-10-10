package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.composeapp.generated.resources.Res
import com.messon.student.composeapp.generated.resources.opensansregular
import com.messon.student.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font


@Composable
fun PrefMenuRow(type: String, text: String, color: Color) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .size(14.dp)
                .border(1.5.dp, color, RoundedCornerShape(3.dp))
                // Adapts automatically (white in light mode, charcoal in dark mode)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(6.dp).background(color, CircleShape))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = type,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "  :  $text",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.opensansregular)),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}