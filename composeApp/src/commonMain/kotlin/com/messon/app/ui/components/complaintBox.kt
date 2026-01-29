package com.messon.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.MoodBad
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font
import tech.annexflow.constraintlayout.compose.ConstraintLayout

@Composable
fun ComplaintBox(
    modifier: Modifier,
    onComplaintClick:()-> Unit,
    isClickable: Boolean
){
    Box(
        modifier = modifier.height(60.dp)
        .background(
            color = Color.White,
            shape = RoundedCornerShape(10.dp)
        )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable(isClickable){
                onComplaintClick()
            }
    ){
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (badMoodIconRef,textRef,expandIconRef)=createRefs()

            Icon(
                imageVector = Icons.Outlined.MoodBad,
                modifier = Modifier.size(20.dp)
                    .constrainAs(badMoodIconRef){
                        start.linkTo(parent.start, margin = 15.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ,
                contentDescription = "complaint"
            )

            Text(
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                text = "Complaint Food",
                color = CustomColors.RichCerulean,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier= Modifier.constrainAs(textRef){
                    start.linkTo(badMoodIconRef.end, margin = 15.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "",
                modifier = Modifier.size(15.dp)
                    .constrainAs(expandIconRef){
                        end.linkTo(parent.end, margin = 15.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}