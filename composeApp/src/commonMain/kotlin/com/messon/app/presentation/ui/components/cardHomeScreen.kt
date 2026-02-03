package com.messon.app.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import messon.composeapp.generated.resources.qr
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import tech.annexflow.constraintlayout.compose.ConstraintLayout

@Composable
fun CardHomeScreen(
    onCardClick:()->Unit,
    modifier: Modifier ,
    borderColor: Color,
    containerColor:Color,
    mealSlot: String,
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
            val (qrIconRef,mealSlotRef,dishesRef)=createRefs()
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
                text =mealSlot,
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                color =borderColor,
                fontSize = 14.sp,
                modifier = Modifier.constrainAs(mealSlotRef){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(qrIconRef.bottom, margin = 15.dp)
                }
            )

            Text(
                text ="Rice, Dal, Soyabean curry, Chicken curry Rice, Dal, Soyabean curry, Chicken curry",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color =Color.Black,
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