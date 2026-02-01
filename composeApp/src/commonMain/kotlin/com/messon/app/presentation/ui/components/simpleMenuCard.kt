package com.messon.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
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
import com.messon.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font
import tech.annexflow.constraintlayout.compose.ConstraintLayout
import tech.annexflow.constraintlayout.compose.Dimension

@Composable
fun SimpleCard(
    getIsExpanded:()-> Unit,
    modifier: Modifier,
    shortDay: String
){
    Box(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(67.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .clickable{
                getIsExpanded()
            }

    ){
        ConstraintLayout(modifier = Modifier.fillMaxSize()){
            val (blueBoxRef,textRef,expandBtnRef)=createRefs()
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.constrainAs(blueBoxRef){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    height= Dimension.fillToConstraints
                }
                    .background(
                        color = CustomColors.RichCerulean,
                        shape = RoundedCornerShape(topStart =15.dp , bottomStart = 15.dp)
                    )
                    .width(65.dp)
            ){
                Text(
                    text = shortDay,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.opensansbold)),
                    color = Color.White
                )
            }

            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text ="Breakfast, Lunch, Dinner",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                color = Color.LightGray,
                modifier = Modifier.constrainAs(textRef){
                    start.linkTo(blueBoxRef.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                    .padding(horizontal = 10.dp)
            )

            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "expand",
                tint = Color.LightGray,
                modifier = Modifier.size(38.dp)
                    .constrainAs(expandBtnRef){
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)

            )
        }
    }
}




