package com.messon.student.app.presentation.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.app.domain.dataModels.DaySelection
import com.messon.student.app.presentation.ui.screens.mess.WEEK_DATA
import com.messon.student.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun PrefDaySelector(
    selectedDayId: String,
    preferences: Map<String, DaySelection>,
    onDaySelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(top = 20.dp)) {

        Text(
            text = "Select day",
            fontSize = 10.sp,
            fontFamily = FontFamily(Font(Res.font.opensanssemiBold) ),
            color = CustomColors.LightGrayBlue,
            modifier = Modifier.padding(start = 24.dp, bottom = 12.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(WEEK_DATA) { day ->

                val isSelected = day.id == selectedDayId
                val hasSelection = (preferences[day.id]?.selectionCount ?: 0) > 0

                val yOffset by animateDpAsState(
                    targetValue = if (isSelected) (-3).dp else 0.dp,
                    label = "offset"
                )

                Column(
                    modifier = Modifier
                        .offset(y = yOffset)
                        .width(70.dp)
                        .height(75.dp)
                        .background(
                            color = if (isSelected) CustomColors.Cream else Color.White,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) CustomColors.HoneyBronze
                            else CustomColors.LightGrayBlue.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onDaySelected(day.id) }
                        .padding(vertical = 6.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = day.label.replaceFirstChar { it.uppercase() },
                        fontSize = 9.sp,
                        fontFamily = FontFamily(Font(Res.font.opensanssemiBold) ),
                        color = if (isSelected) CustomColors.BlackberryCream else CustomColors.LightGrayBlue
                    )

                    Text(
                        text = day.letter,
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansmedium) ),
                        color = if (isSelected) CustomColors.BlackberryCream else CustomColors.LightGrayBlue
                    )

                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .background(
                                color = if (hasSelection) CustomColors.Green500 else Color.Transparent,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

