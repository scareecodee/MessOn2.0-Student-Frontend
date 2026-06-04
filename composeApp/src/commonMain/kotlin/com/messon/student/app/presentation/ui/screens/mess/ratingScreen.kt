package com.messon.student.app.presentation.ui.screens.mess
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.rounded.FreeBreakfast
import androidx.compose.material.icons.rounded.NightlightRound
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.domain.dataModels.MealInfo
import com.messon.student.app.presentation.ui.components.RatingCard
import com.messon.student.app.theme.MessonTheme
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

val meals = listOf(
    MealInfo("B", "Breakfast", Icons.Rounded.FreeBreakfast),
    MealInfo("L", "Lunch", Icons.Rounded.WbSunny),
    MealInfo("D", "Dinner", Icons.Rounded.NightlightRound)
)

@Composable
fun RatingScreen(
    onBackBtnClick:()-> Unit
) {
    var ratings by remember { mutableStateOf(mapOf("B" to 0, "L" to 0, "D" to 0)) }
    var feedback by remember { mutableStateOf(mapOf("B" to "", "L" to "", "D" to "")) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (scrollContent, statusBarBox,backBtnRef,headingRef,heading) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateTopPadding() + 5.dp
                )
                .background(MaterialTheme.colorScheme.outline)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )


        IconButton(
            shape = CircleShape,
            onClick = onBackBtnClick,
            modifier = Modifier.constrainAs(backBtnRef){
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ){
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            maxLines = 1,
            text ="Rate Meal",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            modifier = Modifier
                .constrainAs(headingRef){
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(scrollContent) {
                    top.linkTo(headingRef.bottom, margin = 15.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top =5.dp, bottom = 80.dp)
        ) {
            item {
                HeaderSec(
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            items(meals) { meal->
                val accentColor = when(meal.id) {
                    "B" -> MessonTheme.colors.infoContainer
                    "L" -> MessonTheme.colors.warningContainer
                    else -> MessonTheme.colors.accentContainer
                }

                val iconColor = when(meal.id) {
                    "B" -> MaterialTheme.colorScheme.primary
                    "L" -> MaterialTheme.colorScheme.secondary
                    else -> MaterialTheme.colorScheme.onBackground
                }
                RatingCard(
                    meal = meal,
                    accentColor = accentColor,
                    iconColor = iconColor,
                    rating = ratings[meal.id] ?: 0,
                    feedbackText = feedback[meal.id] ?: "",
                    onRatingChange = { newRating ->
                        ratings = ratings.toMutableMap().apply { put(meal.id, newRating) }
                    },
                    onFeedbackChange = { newFeedback ->
                        feedback = feedback.toMutableMap().apply { put(meal.id, newFeedback) }
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun HeaderSec(
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "How was the food today?",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Rate your meals to help us improve the menu.",
            color =MaterialTheme.colorScheme.outline,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.opensansmedium)),
            fontWeight = FontWeight.Medium
        )
    }
}