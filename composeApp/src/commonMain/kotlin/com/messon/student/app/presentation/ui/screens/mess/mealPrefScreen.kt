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
import com.messon.student.app.presentation.ui.state.FetchMenuState
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.MealType
import com.messon.student.app.utils.WeekDay
import com.messon.student.app.utils.toDayMenuMap
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font
import com.messon.student.app.domain.dataModels.DayModel
import com.messon.student.app.presentation.ui.state.PrefSetupState
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.utils.MealSlot
import multiplatform.network.cmptoast.showToast

@Composable
fun MealPreferenceScreen(
    mealPlannerViewModel: MealPlannerViewModel
) {
    
    var selectedDayId by remember { mutableStateOf("Mon") }
    val prefSetupState by mealPlannerViewModel.prefSetupState.collectAsState()

    LaunchedEffect(Unit){
        mealPlannerViewModel.fetchPrefSetupData()
    }


    LaunchedEffect(prefSetupState){
        if(prefSetupState is PrefSetupState.Error){
            showToast("Error while loading")
            mealPlannerViewModel.clearPrefSetupState()
        }
    }

    val prefSetupData = remember(prefSetupState) {
        (prefSetupState as? PrefSetupState.Success)?.prefSetupData ?: emptyList()
    }

    val preferences = remember(prefSetupData) {
        mutableStateMapOf<String, DaySelection>().apply {

            WEEK_DATA.forEach { day ->

                val dayMeals = prefSetupData.filter {
                    it.day.lowercase().startsWith(day.id.lowercase())
                }
                val breakfast = dayMeals.find { it.mealSlot == MealSlot.BREAKFAST.value }
                val lunch = dayMeals.find { it.mealSlot == MealSlot.LUNCH.value }
                val dinner = dayMeals.find { it.mealSlot == MealSlot.DINNER.value }

                put(
                    day.id,
                    DaySelection(
                        breakfast = if (breakfast?.isCompulsory == true) MealType.VEG else MealType.NONE,
                        lunch = if (lunch?.isCompulsory == true) MealType.VEG else MealType.NONE,
                        dinner = if (dinner?.isCompulsory == true) MealType.VEG else MealType.NONE
                    )
                )
            }
        }
    }

    val dayMenuMap = remember(prefSetupData) {
        prefSetupData.toDayMenuMap()
    }

    val currentDayMenu = dayMenuMap[selectedDayId]

    val totalBookings = preferences.values.sumOf { it.selectionCount }



            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CustomColors.OffWhite)
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
                                    color = CustomColors.BlackberryCream
                                )
                            }
                        }
                    }


                    when(prefSetupState){
                        is PrefSetupState.Loading -> {
                            item {
                                CircularProgressIndicator()
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

                                            if (breakfastData?.isCompulsory == true) {

                                                preferences[selectedDayId] = currentSelection.copy(
                                                    breakfast = type
                                                )

                                            } else {

                                                preferences[selectedDayId] = currentSelection.copy(
                                                    breakfast =
                                                        if (currentSelection.breakfast == type)
                                                            MealType.NONE
                                                        else
                                                            type
                                                )
                                            }
                                        },
                                        iconColor = CustomColors.RichCerulean,
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

                                            if (lunchData?.isCompulsory == true) {

                                                preferences[selectedDayId] = currentSelection.copy(
                                                    lunch =type
                                                )

                                            } else {

                                                preferences[selectedDayId] = currentSelection.copy(
                                                    lunch =
                                                        if (currentSelection.lunch == type)
                                                            MealType.NONE
                                                        else
                                                            type
                                                )
                                            }
                                        },
                                        iconColor = CustomColors.RichCerulean,
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

                                            if (dinnerData?.isCompulsory == true) {

                                                preferences[selectedDayId] = currentSelection.copy(
                                                    dinner = type
                                                )

                                            } else {

                                                preferences[selectedDayId] = currentSelection.copy(
                                                    dinner =
                                                        if (currentSelection.dinner == type)
                                                            MealType.NONE
                                                        else
                                                            type
                                                )
                                            }
                                        },
                                        iconColor = CustomColors.RichCerulean,
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
                    totalBookings = totalBookings,
                    modifier = Modifier.constrainAs(bottomBar) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
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
            .background(CustomColors.RichCerulean)
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
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Rounded.LockClock,
                            contentDescription = null,
                            tint = CustomColors.LightBlue,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Closes in", fontSize = 12.sp, color = CustomColors.LightBlue)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("14h 22m", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }

                Box(modifier = Modifier.width(1.dp).height(32.dp).background(Color.White.copy(alpha = 0.2f)))

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Rounded.DateRange, // Calendar
                            contentDescription = null,
                            tint = CustomColors.LightBlue,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Week of", fontSize = 12.sp, color = CustomColors.LightBlue)
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
    totalBookings: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
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
                    color = CustomColors.LightGrayBlue,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = totalBookings.toString(),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansregular) ),
                        color = CustomColors.BlackberryCream
                    )
                    Text(
                        text = " / 21",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansregular) ),
                    )
                }
            }

            Button(
                onClick = { /* Save Logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = CustomColors.RichCerulean),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save Preference",
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(Res.font.opensanssemiBold) ),
                )
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

val MENU_NOT_LOADED = MealMenu(
    veg = "Menu not loaded",
    nonVeg = "Menu not loaded"
)

data class MealMenu(val veg: String, val nonVeg: String)

data class DayMenu(
    val breakfast: MealMenu,
    val lunch: MealMenu,
    val dinner: MealMenu
)

