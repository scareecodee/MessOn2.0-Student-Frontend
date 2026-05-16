package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.app.domain.dataModels.ComplaintCategory
import com.messon.student.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun CategoryCard(category: ComplaintCategory, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier.width(105.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) CustomColors.LightBlue else Color.White)
            .border(
                width = 2.dp,
                color = if (isSelected) CustomColors.RichCerulean else Color.Transparent,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(18.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isSelected) CustomColors.RichCerulean else CustomColors.OffWhite),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else CustomColors.LightGrayBlue,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = category.label,
            style = TextStyle(
                color = CustomColors.BlackberryCream,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.opensansmedium)),
            )
        )
    }
}