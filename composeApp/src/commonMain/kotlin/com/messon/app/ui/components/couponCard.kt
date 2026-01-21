package com.messon.app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.compose_multiplatform
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun CouponCard(
    modifier: Modifier,
    mealSlot:String,
    date:String,
    borderColor: Color,
    containerColor: Color,
    data: String,
    onCloseBtnClick:()-> Unit,
    logo: Painter= painterResource(Res.drawable.compose_multiplatform)
){
    Card(
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(horizontal =50.dp)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(30.dp)
            ),
        colors = CardDefaults.elevatedCardColors(
            containerColor=containerColor,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text =date,
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                fontSize =16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.size(5.dp))
            QrCode(
                modifier = Modifier.size(140.dp),
                data = data,
                logo = logo
            )
            Spacer(modifier = Modifier.size(5.dp))

            Text(
                text =mealSlot,
                fontFamily = FontFamily(Font(Res.font.opensansbold)),
                fontSize =14.sp,
                maxLines = 1,
                color = borderColor,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(5.dp))
            Button(
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =containerColor,

                ),
                onClick = onCloseBtnClick,
                modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(50.dp)
                    ),
            ){
                Text(
                    text ="Close",
                    color = borderColor,
                    fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                    fontSize =10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}