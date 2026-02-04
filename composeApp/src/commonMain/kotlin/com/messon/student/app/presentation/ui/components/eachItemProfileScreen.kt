package com.messon.student.app.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansmedium
import org.jetbrains.compose.resources.Font
import tech.annexflow.constraintlayout.compose.ConstraintLayout

@Composable
fun EachItemProfileScreen(
    icon: ImageVector,
    title:String,
    onClick:()-> Unit={}
){
    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
            .height(55.dp)
            .clickable{
                onClick()
            }
    ) {
        ConstraintLayout (modifier = Modifier.fillMaxSize()){
            val (iconRef,titleRef,trailingIconRef,)=createRefs()
            Icon(
                imageVector =icon,
                contentDescription ="icon",
                tint = CustomColors.BlackberryCream,
                modifier = Modifier.size(20.dp)
                    .constrainAs(iconRef){
                        start.linkTo(parent.start, margin =23.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text =title,
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.constrainAs(titleRef){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(iconRef.end, margin = 15.dp)
                }
            )

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                tint = CustomColors.RichCerulean ,
                contentDescription = "trailing icon",
                modifier = Modifier.constrainAs(trailingIconRef){
                    end.linkTo(parent.end, margin =8.dp)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                }
                    .padding(horizontal =8.dp)
            )
        }
    }
}