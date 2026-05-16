package com.messon.student.app.presentation.ui.components

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
import com.messon.student.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun HistoryItem(item: ComplaintHistory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    item.date,
                    color = CustomColors.LightGrayBlue,
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (item.status == "Closed") CustomColors.OffWhite else CustomColors.Cream)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        item.status,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                        color = if (item.status == "Closed") Color.Gray else CustomColors.HoneyBronze
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                item.type,
                color = CustomColors.BlackberryCream ,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                )
            Spacer(Modifier.height(4.dp))
            Text(
                item.text,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                color = Color.Gray,
            )
        }
    }
}
