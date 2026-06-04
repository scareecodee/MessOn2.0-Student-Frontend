package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import co.touchlab.kermit.Logger
import com.messon.student.app.presentation.ui.state.FetchMenuState
import com.messon.student.app.utils.MealSlot
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import messon.composeapp.generated.resources.qr
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardHomeScreen(
    onCardClick: () -> Unit,
    modifier: Modifier,
    borderColor: Color,
    containerColor: Color,
    mealSlot: MealSlot,
) {
    val Log = Logger.withTag("CMP_LOG")
    Card(
        onClick = onCardClick,
        modifier = modifier
            .border(
                1.dp,
                borderColor.copy(alpha = 0.8f), // Dynamic borders with a premium subtle alpha opacity
                RoundedCornerShape(18.dp)
            )
            .width(150.dp)
            .heightIn(min = 175.dp, max = 180.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(
            // FIXED: Using the adaptive container color passed from HomeScreen instead of hardcoded Color.White
            containerColor = containerColor
        )
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (qrIconRef, mealSlotRef) = createRefs()

            Icon(
                painter = painterResource(Res.drawable.qr),
                contentDescription = "qr icon",
                // FIXED: Tinted the QR silhouette icon to respect light/dark contrast automatically
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .constrainAs(qrIconRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top, margin = 18.dp)
                    }
                    .size(80.dp)
            )

            Text(
                text = mealSlot.value.replaceFirstChar { it.uppercaseChar() },
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                color = borderColor,
                fontSize = 14.sp,
                modifier = Modifier.constrainAs(mealSlotRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(qrIconRef.bottom, margin = 5.dp)
                    bottom.linkTo(parent.bottom, margin = 5.dp)
                }
            )
        }
    }
}