package com.messon.app.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font
import tech.annexflow.constraintlayout.compose.ConstraintLayout

@Composable
fun AnalyticsTab(
modifier: Modifier,
isClickable: Boolean
){
    Box(
        modifier = modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(15.dp)
        )
            .border(
                width = 1.5.dp,
                color =  CustomColors.OffWhite,
                shape = RoundedCornerShape(15.dp)
            )
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            EachAnalyticsItem(
                title ="Breakfast",
                color = CustomColors.RichCerulean,
                days = listOf(),
                isClickable = isClickable
            )
            EachAnalyticsItem(
                title = "Lunch",
                color = CustomColors.HoneyBronze,
                days = listOf(),
                isClickable = isClickable
            )
            EachAnalyticsItem(
                title = "Dinner",
                color = CustomColors.BlackberryCream,
                days = listOf(),
                isClickable = isClickable
            )
        }
    }

}


@Composable
fun EachAnalyticsItem(
    modifier: Modifier= Modifier,
    title: String,
    color: Color,
    numbOfMealsConsumed: Int=90,
    days: List<String>,
    isClickable: Boolean
){
    var isExpanded by remember { mutableStateOf(false) }

    val boxHeight: Dp by animateDpAsState(
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        targetValue = if (isExpanded) 60.dp else 40.dp,
        label = ""
    )


    Box(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .height(boxHeight)
    ){
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (circleRef,textRef,expandBtnRef,daysRef,noOfMealsRef)=createRefs()

            Box(
                modifier = Modifier.size(8.dp)
                    .background(
                        shape = CircleShape,
                        color = color
                    )
                    .constrainAs(circleRef){
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
            )

            Text(
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                text = title,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(textRef){
                    start.linkTo(circleRef.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                }
                    .padding(horizontal = 10.dp)
            )

            Text(
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                text = numbOfMealsConsumed.toString(),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(noOfMealsRef){
                    end.linkTo(expandBtnRef.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                }
            )

            Icon(
                imageVector = if(isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "expand",
                tint = Color.LightGray,
                modifier = Modifier.constrainAs(expandBtnRef){
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                }
                    .clip(CircleShape)
                    .clickable(isClickable){
                        isExpanded=!isExpanded
                    }
            )

            if(isExpanded){
                Text(
                    text = days.joinToString(),
                    fontFamily = FontFamily(Font(Res.font.opensansregular)),
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    modifier= Modifier.constrainAs(daysRef){
                        start.linkTo(circleRef.start)
                        top.linkTo(circleRef.bottom, margin = 10.dp)
                    }
                        .padding(8.dp)
                )
            }
        }
    }
}
