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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messon.student.app.domain.dataModels.MealInfo
import com.messon.student.app.presentation.ui.components.RatingCard
import com.messon.student.app.presentation.ui.state.SubmitRatingState
import com.messon.student.app.presentation.viewmodel.SubmitRatingVM
import com.messon.student.app.theme.MessonTheme
import com.messon.student.app.utils.MealSlot
import com.messon.student.composeapp.generated.resources.Res
import com.messon.student.composeapp.generated.resources.opensansbold
import com.messon.student.composeapp.generated.resources.opensansmedium
import org.jetbrains.compose.resources.Font
import com.messon.student.composeapp.generated.resources.opensanssemiBold
import multiplatform.network.cmptoast.showToast

@Composable
fun RatingScreen(
    onBackBtnClick: () -> Unit,
    submitRatingVM: SubmitRatingVM
) {
    var breakfastRating by remember { mutableStateOf(0) }
    var lunchRating by remember { mutableStateOf(0) }
    var dinnerRating by remember { mutableStateOf(0) }

    val submitState by submitRatingVM.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(submitState) {
        when (submitState) {
            is SubmitRatingState.Success -> {
                showToast(message = "submitted rating")
                breakfastRating = 0
                lunchRating = 0
                dinnerRating = 0
                submitRatingVM.resetRatings()
            }
            is SubmitRatingState.Error -> {
                showToast("unable to submit rating")
            }
            else -> {}
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (scrollContent, statusBarBox, backBtnRef, headingRef, heading) = createRefs()

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
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            maxLines = 1,
            text = "Rate Meal",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            modifier = Modifier
                .constrainAs(headingRef) {
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
            contentPadding = PaddingValues(top = 5.dp, bottom = 80.dp)
        ) {
            item {
                HeaderSec(
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                val meal = MealInfo(
                    id = MealSlot.BREAKFAST.value,
                    name = "Breakfast",
                    icon = Icons.Rounded.FreeBreakfast
                )

                RatingCard(
                    meal = meal,
                    accentColor = MessonTheme.colors.infoContainer,
                    iconColor = MaterialTheme.colorScheme.primary,
                    rating = breakfastRating,
                    onRatingChange = { newRating ->
                        breakfastRating = newRating
                        submitRatingVM.setBreakfastRating(newRating)
                    },
                    onSubmitClick = {
                        if (breakfastRating > 0) {
                            submitRatingVM.submitRating(mealSlot = MealSlot.BREAKFAST.value)
                        } else {
                            showToast("Please select a rating first")
                        }
                    },
                    isSubmitting = submitState is SubmitRatingState.Loading,
                    isSubmitEnabled = breakfastRating > 0
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                val meal = MealInfo(
                    id = MealSlot.LUNCH.value,
                    name = "Lunch",
                    icon = Icons.Rounded.WbSunny
                )

                RatingCard(
                    meal = meal,
                    accentColor = MessonTheme.colors.warningContainer,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    rating = lunchRating,
                    onRatingChange = { newRating ->
                        lunchRating = newRating
                        submitRatingVM.setLunchRating(newRating)
                    },
                    onSubmitClick = {
                        if (lunchRating > 0) {
                            submitRatingVM.submitRating(mealSlot = MealSlot.LUNCH.value)
                        } else {
                            showToast("Please select a rating first")
                        }
                    },
                    isSubmitting = submitState is SubmitRatingState.Loading,
                    isSubmitEnabled = lunchRating > 0
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                val meal = MealInfo(
                    id = MealSlot.DINNER.value,
                    name = "Dinner",
                    icon = Icons.Rounded.NightlightRound
                )

                RatingCard(
                    meal = meal,
                    accentColor = MessonTheme.colors.accentContainer,
                    iconColor = MaterialTheme.colorScheme.onBackground,
                    rating = dinnerRating,
                    onRatingChange = { newRating ->
                        dinnerRating = newRating
                        submitRatingVM.setDinnerRating(newRating)
                    },
                    onSubmitClick = {
                        if (dinnerRating > 0) {
                            submitRatingVM.submitRating(mealSlot = MealSlot.DINNER.value)
                        } else {
                            showToast("Please select a rating first")
                        }
                    },
                    isSubmitting = submitState is SubmitRatingState.Loading,
                    isSubmitEnabled = dinnerRating > 0
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
            color = MaterialTheme.colorScheme.outline,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.opensansmedium)),
            fontWeight = FontWeight.Medium
        )
    }
}