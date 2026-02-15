package com.messon.student.app.presentation.ui.screens.mainscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.presentation.ui.components.ExpandedMenuCard
import com.messon.student.app.presentation.ui.components.SimpleCard
import com.messon.student.app.presentation.viewmodel.MessMenuViewModel
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.Day
import com.messon.student.app.utils.MessMenuState
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensanssemiBold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font


@Composable
fun MenuScreen(
    modifier: Modifier,
    messMenuViewModel: MessMenuViewModel
) {
    val uiState = messMenuViewModel.uiState.collectAsState(MessMenuState.Idle).value

    val days = listOf(
        Day.MONDAY.value to "M",
        Day.TUESDAY.value to "T",
        Day.WEDNESDAY.value to "W",
        Day.THURSDAY.value to "Th",
        Day.FRIDAY.value to "F",
        Day.SATURDAY.value to "Sa",
        Day.SUNDAY.value to "S"
    )

    LaunchedEffect(uiState) {
        if (uiState is MessMenuState.Error) {
            showToast(uiState.message)
            messMenuViewModel.clearMenuError()
        }
    }

    var expandedDay by remember { androidx.compose.runtime.mutableStateOf<String?>(null) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        val (statusBarBox, headingRef, listRef) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        Text(
            text = "Mess Menu",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(parent.top, margin = 60.dp)
            }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .constrainAs(listRef) {
                    top.linkTo(headingRef.bottom, margin = 25.dp)
                    bottom.linkTo(parent.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth()
        ) {
            items(days) { (day, shortDay) ->

                val isExpanded = expandedDay == day

                AnimatedContent(
                    targetState = isExpanded,
                    label = "ExpandCollapse",
                    transitionSpec = {
                        (fadeIn(tween(550, delayMillis = 100)) +
                                scaleIn(initialScale = 0.92f,
                                    animationSpec = tween(550, delayMillis = 100)
                                ))
                            .togetherWith(fadeOut(tween(150)))
                    }
                ) { expanded ->

                    if (!expanded) {
                        SimpleCard(
                            modifier = Modifier,
                            shortDay = shortDay,
                            getIsExpanded = {
                                expandedDay = day       // ✅ collapse others
                                messMenuViewModel.onMenuCardClick(day)
                            }
                        )
                    } else {
                        when (uiState) {
                            is MessMenuState.Success -> {
                                val menu = uiState.eachDayMenu
                                ExpandedMenuCard(
                                    modifier = Modifier,
                                    day = day,
                                    breakfastDishes = menu.breakfast.dishesName,
                                    lunchDishes = menu.lunch.dishesName,
                                    dinnerDishes = menu.dinner.dishesName,
                                    getIsExpanded = {
                                        expandedDay = null
                                    }
                                )
                            }

                            is MessMenuState.Loading -> {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            is MessMenuState.Error,
                            is MessMenuState.Idle -> {
                                ExpandedMenuCard(
                                    modifier = Modifier,
                                    day = day,
                                    breakfastDishes = null,
                                    lunchDishes = null,
                                    dinnerDishes = null,
                                    getIsExpanded = {
                                        expandedDay = null
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
