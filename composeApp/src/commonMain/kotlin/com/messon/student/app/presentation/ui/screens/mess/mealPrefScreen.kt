package com.messon.student.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.domain.dataModels.DaySelection
import com.messon.student.app.presentation.ui.components.PrefDaySelector
import com.messon.student.app.presentation.ui.components.PrefMealCard
import com.messon.student.app.utils.WeekDay
import com.messon.student.app.utils.toDayMenuMap
import org.jetbrains.compose.resources.Font
import com.messon.student.composeapp.generated.resources.opensanssemiBold
import com.messon.student.app.domain.dataModels.DayModel
import com.messon.student.app.presentation.ui.state.PrefSetupState
import com.messon.student.app.presentation.ui.state.SavePrefState
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.theme.MessonTheme
import com.messon.student.app.utils.MealSlot
import com.messon.student.composeapp.generated.resources.Res
import com.messon.student.composeapp.generated.resources.opensansmedium
import com.messon.student.composeapp.generated.resources.opensansregular
import multiplatform.network.cmptoast.showToast

data class MealMenu(val veg: String, val nonVeg: String)

@Composable
fun MealPreferenceScreen(
    mealPlannerViewModel: MealPlannerViewModel
) {
    var selectedDayId by remember { mutableStateOf("Mon") }
    val prefSetupState by mealPlannerViewModel.prefSetupState.collectAsState()
    val savePrefState by mealPlannerViewModel.savePrefState.collectAsState()

    LaunchedEffect(Unit){
        mealPlannerViewModel.fetchPrefSetupData()
    }

    LaunchedEffect(prefSetupState){
        if(prefSetupState is PrefSetupState.Error){
            showToast("Error while loading")
            mealPlannerViewModel.clearPrefSetupState()
        }
    }

    LaunchedEffect(savePrefState){
        when(savePrefState){
            is SavePrefState.Error -> {
                showToast((savePrefState as SavePrefState.Error).message)
            }
            is SavePrefState.Success -> {
                showToast("saved your meal pref.")
            }
            else -> {}
        }
        mealPlannerViewModel.clearSavePrefState()
    }

    val prefSetupData = remember(prefSetupState) {
        (prefSetupState as? PrefSetupState.Success)?.prefSetupData ?: emptyList()
    }

    LaunchedEffect(prefSetupData) {
        if (prefSetupData.isNotEmpty()) {
            mealPlannerViewModel.initPreferences(prefSetupData)
        }
    }

    val preferences by mealPlannerViewModel.preferences.collectAsState()

    val dayMenuMap = remember(prefSetupData) {
        prefSetupData.toDayMenuMap()
    }

    val currentDayMenu = dayMenuMap[selectedDayId]
    val totalBookings = preferences.values.sumOf { it.selectionCount }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (header, content, bottomBar) = createRefs()

        HeaderSection(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(bottomBar.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .padding(bottom = 24.dp)
        ) {
            item {
                PrefDaySelector(
                    selectedDayId = selectedDayId,
                    preferences = preferences,
                    onDaySelected = { selectedDayId = it }
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "${WEEK_DATA.find { it.id == selectedDayId }?.label}".replaceFirstChar { it.uppercase() },
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold) ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            when(prefSetupState){
                is PrefSetupState.Loading -> {
                    item {
                        Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is PrefSetupState.Success->{
                    item {
                        val currentSelection = preferences[selectedDayId] ?: DaySelection()
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val breakfastData = currentDayMenu?.get(MealSlot.BREAKFAST)
                            PrefMealCard(
                                title = MealSlot.BREAKFAST.value.replaceFirstChar { it.uppercase() },
                                icon = Icons.Rounded.WbTwilight,
                                menu = MealMenu(
                                    veg = breakfastData?.vegDish ?: "Not Available",
                                    nonVeg = breakfastData?.nonVegDish ?: "Not Available"
                                ),
                                selectedType = currentSelection.breakfast,
                                onSelect = { type ->
                                    mealPlannerViewModel.updateMeal(
                                        dayId = selectedDayId,
                                        mealSlot = MealSlot.BREAKFAST,
                                        type = type,
                                        isCompulsory = breakfastData?.isCompulsory == true
                                    )
                                },
                                iconColor = MaterialTheme.colorScheme.primary,
                                isVegSelectable = breakfastData?.vegAvailability == true,
                                isNonVegSelectable = breakfastData?.nonVegAvailability == true
                            )

                            val lunchData = currentDayMenu?.get(MealSlot.LUNCH)
                            PrefMealCard(
                                title = MealSlot.LUNCH.value.replaceFirstChar { it.uppercase() },
                                icon = Icons.Default.WbSunny,
                                menu = MealMenu(
                                    veg = lunchData?.vegDish ?: "Not Available",
                                    nonVeg = lunchData?.nonVegDish ?: "Not Available"
                                ),
                                selectedType = currentSelection.lunch,
                                onSelect = { type ->
                                    mealPlannerViewModel.updateMeal(
                                        dayId = selectedDayId,
                                        mealSlot = MealSlot.LUNCH,
                                        type = type,
                                        isCompulsory = lunchData?.isCompulsory == true
                                    )
                                },
                                iconColor = MaterialTheme.colorScheme.secondary,
                                isVegSelectable = lunchData?.vegAvailability == true,
                                isNonVegSelectable = lunchData?.nonVegAvailability == true
                            )

                            val dinnerData = currentDayMenu?.get(MealSlot.DINNER)
                            PrefMealCard(
                                title = MealSlot.DINNER.value.replaceFirstChar { it.uppercase() },
                                icon = Icons.Rounded.Nightlight,
                                menu = MealMenu(
                                    veg = dinnerData?.vegDish ?: "Not Available",
                                    nonVeg = dinnerData?.nonVegDish ?: "Not Available"
                                ),
                                selectedType = currentSelection.dinner,
                                onSelect = { type ->
                                    mealPlannerViewModel.updateMeal(
                                        dayId = selectedDayId,
                                        mealSlot = MealSlot.DINNER,
                                        type = type,
                                        isCompulsory = dinnerData?.isCompulsory == true
                                    )
                                },
                                iconColor = MaterialTheme.colorScheme.tertiary,
                                isVegSelectable = dinnerData?.vegAvailability == true,
                                isNonVegSelectable = dinnerData?.nonVegAvailability == true
                            )
                        }
                    }
                }
                else -> {}
            }
        }

        BottomBarSection(
            isLoading = savePrefState is SavePrefState.Loading,
            totalBookings = totalBookings,
            modifier = Modifier.constrainAs(bottomBar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onSaveBtnClick = {
                mealPlannerViewModel.saveMealPref()
            }
        )
    }
}

@Composable
fun HeaderSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        Column {
            Text(
                text = "Meal Preference",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold) ),
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(16.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Rounded.LockClock,
                            contentDescription = null,
                            tint = MessonTheme.colors.infoContainer,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Closes in", fontSize = 12.sp, color = MessonTheme.colors.infoContainer)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("14h 22m", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }

                Box(modifier = Modifier.width(1.dp).height(32.dp).background(Color.White.copy(alpha = 0.2f)))

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Rounded.DateRange,
                            contentDescription = null,
                            tint = MessonTheme.colors.infoContainer,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Week of", fontSize = 12.sp, color = MessonTheme.colors.infoContainer)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("16 Feb - 22 Feb", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun BottomBarSection(
    isLoading: Boolean,
    totalBookings: Int,
    modifier: Modifier = Modifier,
    onSaveBtnClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 16.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Meals Booked",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.opensansmedium) ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = totalBookings.toString(),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansregular) ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = " / 21",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily(Font(Res.font.opensansregular) ),
                    )
                }
            }

            Button(
                onClick = onSaveBtnClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint=Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save Preference",
                    fontSize = 10.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(Res.font.opensanssemiBold) ),
                )
                Spacer(modifier = Modifier.width(8.dp))
                if(isLoading){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}

val WEEK_DATA = listOf(
    DayModel("Mon", WeekDay.MONDAY.value, "M"),
    DayModel("Tue", WeekDay.TUESDAY.value, "T"),
    DayModel("Wed", WeekDay.WEDNESDAY.value, "W"),
    DayModel("Thu", WeekDay.THURSDAY.value, "T"),
    DayModel("Fri", WeekDay.FRIDAY.value, "F"),
    DayModel("Sat", WeekDay.SATURDAY.value, "S"),
    DayModel("Sun", WeekDay.SUNDAY.value, "S")
)