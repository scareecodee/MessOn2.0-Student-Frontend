package com.messon.student.app.presentation.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.MealType
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun AppHeader(timeLeft: String, weekRange: String) {
    Surface(
        color = CustomColors.RichCerulean,
        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
        shadowElevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text("Meal Preference",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansbold)),
                        fontWeight = FontWeight.Black)
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth().background(Color.Black.copy(alpha = 0.1f), RoundedCornerShape(16.dp)).padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Closes in",
                        fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(timeLeft, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(Res.font.opensansregular))
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "Week of",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                    )
                    Text(weekRange, color = Color.White, fontSize = 10.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansregular))
                    )
                }
            }
        }
    }
}



@Composable
fun DayItem(day: String, isSelected: Boolean, hasSelection: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) CustomColors.HoneyBronze else CustomColors.LightGrayBlue.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(day.take(3).uppercase(),
            fontSize = 10.sp, fontWeight = FontWeight.Bold,
            color = CustomColors.BlackberryCream,
            fontFamily = FontFamily(Font(Res.font.opensansregular))
        )
        Text(day.take(1), fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = CustomColors.BlackberryCream,
            fontFamily = FontFamily(Font(Res.font.opensanssemiBold))

        )
        if (hasSelection) {
            Box(modifier = Modifier.size(6.dp).background(Color(0xFF10B981), RoundedCornerShape(50)))
        }
    }
}

@Composable
fun MealOption(label: String, icon: ImageVector, isSelected: Boolean, activeColor: Color, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) activeColor.copy(alpha = 0.1f) else Color(0xFFF9FAFB))
            .border(2.dp, if (isSelected) activeColor else Color.Transparent, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null,
            tint = if (isSelected) activeColor else Color.LightGray
        )
        Text(label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) activeColor else Color.Gray,
            fontFamily = FontFamily(Font(Res.font.opensansmedium))
        )
    }
}

@Composable
fun MealCard(title: String, currentSelection: MealType, onSelectionChange: (MealType) -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = CustomColors.HoneyBronze,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(title,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                    color = CustomColors.BlackberryCream)
            }
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.weight(1f)) {
                    MealOption("Veg", Icons.Default.CheckCircle, currentSelection == MealType.VEG, Color(0xFF10B981)) {
                        onSelectionChange(MealType.VEG)
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    MealOption("Non-Veg", Icons.Default.Favorite, currentSelection == MealType.NON_VEG, Color(0xFFEF4444)) {
                        onSelectionChange(MealType.NON_VEG)
                    }
                }
            }
        }
    }
}