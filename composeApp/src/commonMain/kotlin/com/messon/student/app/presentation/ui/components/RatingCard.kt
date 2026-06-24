package com.messon.student.app.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.app.domain.dataModels.MealInfo
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun RatingCard(
    meal: MealInfo,
    rating: Int,
    onRatingChange: (Int) -> Unit,
    accentColor: Color,
    iconColor: Color,
    onSubmitClick: () -> Unit,
    isSubmitting: Boolean,
    isSubmitEnabled: Boolean
) {
    val isRated = rating > 0
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(rating) {
        if (rating == 0) {
            showSuccess = false
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = accentColor),
        shape = RoundedCornerShape(40.dp),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clip(RoundedCornerShape(30.dp))
            .border(
                width = 1.dp,
                color = if (isRated) Color.White.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(28.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White.copy(alpha = 0.8f),
                    shadowElevation = 1.dp,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = meal.icon,
                            contentDescription = meal.name,
                            tint = if (isRated) iconColor else MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = meal.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                    )
                    Text(
                        text = if (showSuccess) "Feedback submitted" else if (isRated) "Rating recorded" else "Tap a star to rate",
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                    )
                }

                AnimatedVisibility(
                    visible = showSuccess || isRated,
                    enter = scaleIn() + fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    val checkColor by animateColorAsState(
                        targetValue = if (showSuccess) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                        label = "CheckmarkColor"
                    )
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 2.dp,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Rounded.Check,
                                contentDescription = "Rated",
                                tint = checkColor,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 1..5) {
                    val starColor by animateColorAsState(
                        targetValue = if (i <= rating) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                        animationSpec = tween(300),
                        label = "StarColor"
                    )

                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Rate $i stars",
                        tint = starColor,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                enabled = !showSuccess && !isSubmitting
                            ) {
                                onRatingChange(i)
                                showSuccess = false
                            }
                    )
                }
            }

            AnimatedVisibility(
                visible = isRated && !showSuccess,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Button(
                    onClick = {
                        onSubmitClick()
                        if (isSubmitEnabled && !isSubmitting) {
                            // loading indicator add kr dena baad me
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    enabled = isSubmitEnabled && !isSubmitting && !showSuccess
                ) {
                    Text(
                        text = if (isSubmitting) "Submitting..." else "Submit Response",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                        maxLines = 1,
                        color = Color.White
                    )
                }
            }
        }
    }
}