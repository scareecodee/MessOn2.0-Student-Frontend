package com.messon.app.ui.screens.mainscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.app.domain.dataModels.DayWiseMenu
import com.messon.app.ui.components.ExpandedMenuCard
import com.messon.app.ui.components.SimpleCard
import com.messon.app.utils.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font
import tech.annexflow.constraintlayout.compose.ConstraintLayout
import tech.annexflow.constraintlayout.compose.Dimension

@Composable
fun MenuScreen(
    modifier: Modifier
){
    val dayWiseMenuList=listOf(
        DayWiseMenu(
            day="Monday",
            breakfastDishes =listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            lunchDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            dinnerDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            shortDay = "M"
        ),
        DayWiseMenu(
            day="Tuesday",
            breakfastDishes =listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            lunchDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            dinnerDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            shortDay = "T"
        ),
        DayWiseMenu(
            day="Wednesday",
            breakfastDishes =listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            lunchDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            dinnerDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            shortDay = "W"
        ),
        DayWiseMenu(
            day="Thursday",
            breakfastDishes =listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            lunchDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            dinnerDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            shortDay = "Th"
        ),
        DayWiseMenu(
            day="Friday",
            breakfastDishes =listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            lunchDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            dinnerDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            shortDay = "F"

        ),
        DayWiseMenu(
            day="Saturday",
            breakfastDishes =listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            lunchDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            dinnerDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            shortDay = "Sa"
        ),
        DayWiseMenu(
            day="Sunday",
            breakfastDishes =listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            lunchDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            dinnerDishes = listOf("Rice, Dal, Soyabean curry, Chicken curry"),
            shortDay = "S"
        )
    )

    val expandedMap = remember {
        mutableStateMapOf<String, Boolean>()
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        val (statusBarBox,headingRef,listRef)=createRefs()
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()+5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text ="Mess Menu",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
            modifier = Modifier
                .constrainAs(headingRef){
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin =60.dp)
                }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier= Modifier.constrainAs(listRef){
                top.linkTo(headingRef.bottom, margin = 25.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height= Dimension.fillToConstraints
            }
                .fillMaxWidth()
        ) {
            items(dayWiseMenuList) { item ->

                val isExpanded = expandedMap[item.day] ?: false

                AnimatedContent(
                    targetState = isExpanded,
                    label = "ExpandCollapse"
                ) { expanded ->

                    if (!expanded) {
                        SimpleCard(
                            modifier = Modifier,
                            shortDay = item.shortDay,
                            getIsExpanded = {
                                expandedMap[item.day] = true
                            }
                        )
                    } else {
                        ExpandedMenuCard(
                            modifier = Modifier,
                            day = item.day,
                            breakfastDishes = item.breakfastDishes.joinToString(),
                            lunchDishes = item.lunchDishes.joinToString(),
                            dinnerDishes = item.dinnerDishes.joinToString(),
                            getIsExpanded = {
                                expandedMap[item.day] = false
                            }
                        )
                    }
                }
            }

        }

    }

}