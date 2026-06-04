package com.messon.student.app.presentation.ui.screens.mess

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.domain.dataModels.Menu
import com.messon.student.app.presentation.ui.components.MealCard
import com.messon.student.app.presentation.ui.components.MealTypeSelector
import com.messon.student.app.presentation.ui.state.FetchMenuState
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import com.messon.student.app.theme.MessonTheme
import com.messon.student.app.utils.MealSlot
import com.messon.student.app.utils.convertDay
import com.messon.student.app.utils.getDish
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensanssemiBold
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font

@Composable
fun ViewMessMenuScreen(
    onBackBtnClick: () -> Unit,
    menuFetchVM: MenuFetchVM
) {
    var selectedDay by remember { mutableStateOf("Mon") }
    var dietTab by remember { mutableStateOf(0) }

    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val uiState by menuFetchVM.uiState.collectAsState()
    var menu by remember { mutableStateOf<List<Menu>>(emptyList()) }

    LaunchedEffect(Unit) {
        menuFetchVM.fetchMenu()
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is FetchMenuState.Error -> {
                showToast("Error while loading menu")
            }
            is FetchMenuState.Success -> {
                menu = (uiState as FetchMenuState.Success).menus
            }
            else -> {}
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (scrollContent, statusBarBox, backBtnRef, headingRef) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MaterialTheme.colorScheme.background)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        IconButton(
            onClick = { onBackBtnClick() },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, 10.dp)
            }
        ) {
            Icon(
                Icons.Rounded.ChevronLeft,
                contentDescription = "back",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onBackground // Dynamic icon tint
            )
        }

        Text(
            text = "Mess Menu",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            color = MaterialTheme.colorScheme.onBackground, // Dynamic text color
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(scrollContent) {
                    top.linkTo(backBtnRef.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 12.dp)
        ) {
            item {
                Text(
                    text = "Weekly Menu",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        end = 12.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(days) { day ->
                        val active = selectedDay == day

                        val scale by animateFloatAsState(
                            targetValue = if (active) 1.1f else 1f, label = ""
                        )

                        val bg by animateColorAsState(
                            targetValue = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            label = ""
                        )

                        val textColor by animateColorAsState(
                            targetValue = if (active) Color.White else MaterialTheme.colorScheme.onBackground,
                            label = ""
                        )

                        Card(
                            modifier = Modifier
                                .size(64.dp, 80.dp)
                                .scale(scale)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { selectedDay = day },
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(containerColor = bg),
                            elevation = CardDefaults.cardElevation(
                                if (active) 12.dp else 2.dp
                            )
                        ) {
                            Box(
                                Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    day,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                                    color = textColor
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(12.dp))
            }

            item {
                MealTypeSelector(
                    selected = dietTab,
                    onSelect = { dietTab = it }
                )
            }

            item {
                Spacer(Modifier.height(24.dp))
            }

            item {
                MealCard(
                    mealSlot = "Breakfast",
                    dishes = getDish(menu, convertDay(selectedDay).value, MealSlot.BREAKFAST.value, dietTab),
                    tint = MessonTheme.colors.infoContainer,
                    accent = MaterialTheme.colorScheme.primary,
                    icon = Icons.Default.Coffee
                )
            }

            item {
                MealCard(
                    mealSlot = "Lunch",
                    dishes = getDish(menu, convertDay(selectedDay).value, MealSlot.LUNCH.value, dietTab),
                    tint = MessonTheme.colors.warningContainer,
                    accent = MaterialTheme.colorScheme.secondary,
                    icon = Icons.Default.WbSunny
                )
            }

            item {
                MealCard(
                    mealSlot = "Dinner",
                    dishes = getDish(menu, convertDay(selectedDay).value, MealSlot.DINNER.value, dietTab),
                    tint = MessonTheme.colors.accentContainer,
                    accent = MaterialTheme.colorScheme.tertiary, // Used tertiary for Dinner accent in Dark Theme compatibility
                    icon = Icons.Default.NightsStay
                )
            }

            item {
                Spacer(Modifier.height(100.dp))
            }
        }
    }
}