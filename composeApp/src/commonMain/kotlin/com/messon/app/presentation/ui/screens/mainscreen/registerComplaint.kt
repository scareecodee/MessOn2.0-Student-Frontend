package com.messon.app.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.app.ui.components.TextFieldItem
import com.messon.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import org.jetbrains.compose.resources.Font
import tech.annexflow.constraintlayout.compose.ConstraintLayout

@Composable
fun RegisterComplaintScreen(
    modifier: Modifier,
    heading: String,
    subHeading: String,
    onSendClick:()->Unit
){
    var description by remember{ mutableStateOf("") }
    var subject by remember{ mutableStateOf("") }
    val issueTYpe by remember{ mutableStateOf("") }
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
            .background(color = Color.White)
    ){
        val (statusBarBox,headingRef,subHeadingRef,listRef)=createRefs()
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
            text =heading,
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            color = CustomColors.RichCerulean,
            modifier = Modifier.constrainAs(headingRef){
                top.linkTo(statusBarBox.bottom, margin = 30.dp)
                start.linkTo(parent.start)
            }
                .padding(horizontal = 15.dp)
        )

        Text(
            text =subHeading,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.opensansmedium)),
            color = Color.LightGray,
            modifier = Modifier.constrainAs(subHeadingRef){
                top.linkTo(headingRef.bottom, margin = 7.dp)
                start.linkTo(parent.start)
            }
                .padding(horizontal = 15.dp)

        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .constrainAs(listRef){
                    top.linkTo(subHeadingRef.bottom)
                    start.linkTo(headingRef.start)
                }
                .padding(horizontal = 16.dp)
        ) {

            item {
                TextFieldItem(
                    singleLine = false,
                    modifier = Modifier,
                    heading = "Subject",
                    value = subject,
                    headingColor = CustomColors.BlackberryCream,
                    onValueChange = {
                        subject=it
                    },
                    textFieldHeight = 80.dp
                )
            }
            item {
                TextFieldItem(
                    singleLine = false,
                    modifier = Modifier,
                    heading = "Description",
                    value =description,
                    headingColor = CustomColors.BlackberryCream,
                    onValueChange = {
                        description=it
                    },
                    textFieldHeight = 150.dp
                )
            }
            item {
                Spacer(modifier = modifier.height(30.dp))
                Button(
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth().height(55.dp)
                        .padding(horizontal = 10.dp),
                    onClick = onSendClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.RichCerulean
                    )
                ){
                    Text(
                        text = "Submit Report",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                        modifier = Modifier.padding(horizontal = 17.dp)
                    )

                    Icon(
                        imageVector = Icons.Outlined.Send,
                        contentDescription = "send",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}