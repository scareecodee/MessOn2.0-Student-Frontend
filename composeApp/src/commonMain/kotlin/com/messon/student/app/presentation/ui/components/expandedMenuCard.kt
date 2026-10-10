package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import com.messon.student.composeapp.generated.resources.opensansbold
import com.messon.student.composeapp.generated.resources.opensansregular
import com.messon.student.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font


@Composable
fun ExpandedMenuCard(
    getIsExpanded:()-> Unit,
    modifier: Modifier,
    day: String,
    breakfastDishes: String?,
    lunchDishes: String?,
    dinnerDishes: String?
){
  Box(
      modifier = modifier.fillMaxWidth()
          .padding(horizontal = 16.dp)
          .background(
              color = Color.White,
              shape = RoundedCornerShape(15.dp)
          )
          .border(
              width = 0.5.dp,
              color = Color.LightGray,
              shape = RoundedCornerShape(15.dp)
          )

  ){
      Column(
          verticalArrangement = Arrangement.spacedBy(15.dp),
          modifier = Modifier.fillMaxSize()
              .padding(10.dp)
      ) {
          Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
              Box(modifier = Modifier.fillMaxWidth()){
                  Text(
                      text =day,
                      fontSize = 16.sp,
                      color = MaterialTheme.colorScheme.secondary,
                      fontFamily = FontFamily(
                          Font(Res.font.opensanssemiBold)
                      ),
                      modifier = Modifier.align(Alignment.Center)
                  )

                  Icon(
                      imageVector =Icons.Filled.KeyboardArrowUp ,
                      contentDescription = "expand",
                      tint = Color.LightGray,
                      modifier = Modifier.size(25.dp)
                      .align(Alignment.CenterEnd)
                          .clip(CircleShape)
                          .clickable{
                              getIsExpanded()
                          }
                  )
              }

          }

          EachItemExpandedMenuCard(
              modifier = Modifier.align(Alignment.Start),
              mealSlot = "Breakfast",
              dishes = breakfastDishes?:"Unable to load",
              color = MaterialTheme.colorScheme.secondary
          )

          EachItemExpandedMenuCard(
              modifier = Modifier.align(Alignment.Start),
              mealSlot = "Lunch",
              dishes = lunchDishes?:"Unable to load",
              color =MaterialTheme.colorScheme.secondary
          )

          EachItemExpandedMenuCard(
              modifier = Modifier.align(Alignment.Start),
              mealSlot = "Dinner",
              dishes = dinnerDishes?:"Unable to load",
              color = MaterialTheme.colorScheme.onBackground
          )
      }
  }

}



@Composable
fun EachItemExpandedMenuCard(
    modifier: Modifier,
    mealSlot: String,
    dishes: String,
    color: Color
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier =modifier.padding(start = 10.dp)
    ) {
        Text(
            text = mealSlot,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.opensansregular)),
            color = Color.LightGray,
            modifier = Modifier.width(65.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))

        Box(
            modifier = Modifier.size(7.dp)
                .background(
                    shape = CircleShape,
                    color = color
                )
        )

        Text(
            text = dishes,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.opensansregular)),
            color = if(dishes.isEmpty())Color.Red else Color.Black,
            modifier= Modifier.padding(start =10.dp, end =7.dp)
        )
    }
}