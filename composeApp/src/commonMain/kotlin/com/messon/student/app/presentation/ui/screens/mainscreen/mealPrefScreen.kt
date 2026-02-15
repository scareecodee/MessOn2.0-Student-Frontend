package com.messon.student.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import com.messon.student.app.domain.dataModels.DayPreference
import com.messon.student.app.presentation.ui.components.AppHeader
import com.messon.student.app.presentation.ui.components.DayItem
import com.messon.student.app.presentation.ui.components.MealCard
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.MealType
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font


@Composable
fun MealPreferenceScreen(
    mealPlannerViewModel: MealPlannerViewModel
) {
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val weeklyState by mealPlannerViewModel.weeklyState.collectAsState()
    val activeDay by mealPlannerViewModel.activeDay.collectAsState()
    val isSaved by mealPlannerViewModel.isSaved.collectAsState()


    val weeklyPreferencesList: List<DayPreference> =
        days.map { day -> weeklyState[day]!! }

    val Log= Logger.withTag("CMP_LOG")
    Log.d(
        tag = "meal", message = {weeklyPreferencesList.toString()}
    )


    val totalMealsBooked by remember {
        derivedStateOf {
            weeklyState.values.sumOf { pref ->
                var count = 0
                if (pref.breakfast != MealType.NONE) count++
                if (pref.lunch != MealType.NONE) count++
                if (pref.dinner != MealType.NONE) count++
                count
            }
        }
    }


    Column(modifier = Modifier.fillMaxSize().background(CustomColors.LightLavender)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(CustomColors.RichCerulean)
        )

        Scaffold(
            topBar = {
                AppHeader(timeLeft = "14h 22m", weekRange = "16 Feb - 22 Feb")
            },
            containerColor = CustomColors.OffWhite,
            bottomBar = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    shadowElevation = 16.dp,
                    color = Color.White.copy(alpha = 0.95f)
                ) {
                    Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Meals Booked",
                                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                                fontSize = 10.sp,
                                color = Color.Gray)
                            Text(
                                text = "$totalMealsBooked / 21",
                                fontSize = 14.sp,
                                color = CustomColors.BlackberryCream,
                                fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                            )
                        }
                        Button(
                            onClick = { mealPlannerViewModel.save() },
                            modifier = Modifier.weight(2f).height(52.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSaved) Color(0xFF10B981) else CustomColors.RichCerulean
                            )
                        ) {
                            Icon(if (isSaved) Icons.Default.Check else Icons.Default.Done, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(if (isSaved) "Confirmed" else "Save Preference",
                                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        ) { padding ->
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(top=padding.calculateTopPadding(),bottom = padding.calculateBottomPadding())) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Select Day", fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                        color = CustomColors.BlackberryCream.copy(alpha = 0.5f))
                    Spacer(Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(days) { day ->
                            val hasPref = weeklyState[day]?.let {
                                it.breakfast != MealType.NONE || it.lunch != MealType.NONE || it.dinner != MealType.NONE
                            } ?: false
                            DayItem(
                                day = day,
                                isSelected = activeDay == day,
                                hasSelection = hasPref,
                                onClick = { mealPlannerViewModel.setActiveDay(day) }
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(activeDay, fontSize =15.sp,
                                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                                fontWeight = FontWeight.Black,
                                color = CustomColors.BlackberryCream
                            )
                            val isWeekend = activeDay == "Saturday" || activeDay == "Sunday"
                            Surface(
                                color = if (isWeekend) CustomColors.Cream else CustomColors.LightBlue,
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                                    text = if (isWeekend) "Optional" else "Compulsory",
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isWeekend) CustomColors.HoneyBronze else CustomColors.RichCerulean
                                )
                            }
                        }

                        TextButton(
                            onClick = {
                                mealPlannerViewModel.resetDay()
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = CustomColors.BlackberryCream.copy(alpha = 0.7f))
                        ) {
                            Icon(Icons.Default.Refresh,
                                contentDescription = null,
                                tint = CustomColors.BlackberryCream,
                                modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Reset Day",
                                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                                color = CustomColors.BlackberryCream,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    val currentDayPref = weeklyState[activeDay]!!

                    MealCard("Breakfast", currentDayPref.breakfast) {
                      mealPlannerViewModel.updateBreakfast(it)
                    }

                    Spacer(Modifier.height(12.dp))

                    MealCard("Lunch", currentDayPref.lunch) {
                        mealPlannerViewModel.updateLunch(it)

                    }

                    Spacer(Modifier.height(12.dp))

                    MealCard("Dinner", currentDayPref.dinner) {
                        mealPlannerViewModel.updateDinner(it)
                    }

                    Spacer(Modifier.height(24.dp))


                    Surface(
                        color = CustomColors.LightBlue.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, Color(0xFFBFDBFE))
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = CustomColors.RichCerulean, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "Selections for next week must be finalized by Sunday midnight. Weekdays default to Veg. Weekends are excluded unless chosen.",
                                fontSize = 11.sp,
                                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                                color = CustomColors.RichCerulean,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}