package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.messon.student.app.utils.MealSlot
import com.messon.student.app.utils.MessMenuState
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import messon.composeapp.generated.resources.qr
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardHomeScreen(
    onCardClick:()->Unit,
    modifier: Modifier ,
    borderColor: Color,
    containerColor:Color,
    mealSlot: MealSlot,
    menuState: MessMenuState
){
    Card(
        onClick = onCardClick,
        modifier = modifier.border(
            1.dp,borderColor,RoundedCornerShape(18.dp)
        )
            .width(150.dp)
            .heightIn(min=175.dp, max=180.dp)
        ,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor
        )
    ) {
        ConstraintLayout (
            modifier = Modifier.fillMaxSize()
        ){
            val (qrIconRef,mealSlotRef,dishesRef,circularProgIndic)=createRefs()
            Icon(
                painter = painterResource(Res.drawable.qr),
                contentDescription ="qr icon",
                tint = Color.Unspecified,
                modifier = Modifier.constrainAs(qrIconRef){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 18.dp)
                }
                    .size(50.dp)
            )

            Text(
                text =mealSlot.value,
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                color =borderColor,
                fontSize = 14.sp,
                modifier = Modifier.constrainAs(mealSlotRef){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(qrIconRef.bottom, margin = 15.dp)
                }
            )

            when(menuState){
                is MessMenuState.Loading -> {
                  CircularProgressIndicator(
                      color = borderColor,
                      strokeWidth = 3.dp,
                      strokeCap = StrokeCap.Round,
                      modifier = Modifier.size(30.dp)
                          .constrainAs(circularProgIndic){
                              bottom.linkTo(parent.bottom)
                          start.linkTo(parent.start, margin = 8.dp)
                          end.linkTo(parent.end, margin = 8.dp)
                          top.linkTo(mealSlotRef.bottom)
                      }
                  )
                }

                is MessMenuState.Success -> {
                    val menu = menuState.eachDayMenu

                    val dishes = when(mealSlot){
                        MealSlot.BREAKFAST -> menu.breakfast.dishesName
                        MealSlot.LUNCH -> menu.lunch.dishesName
                        MealSlot.DINNER -> menu.dinner.dishesName
                    }

                    Text(
                        text =dishes,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansregular)),
                        modifier = Modifier.constrainAs(dishesRef){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(mealSlotRef.bottom, margin = 12.dp)
                        }
                            .padding(horizontal = 8.dp)
                    )
                }

                is MessMenuState.Idle -> Unit
                is MessMenuState.Error -> {
                    Text(
                        text = "unable to load",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansregular)),
                        modifier = Modifier.constrainAs(dishesRef){
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(mealSlotRef.bottom, margin = 12.dp)
                        }
                            .padding(horizontal = 8.dp)
                    )
                }
            }

        }
    }
}