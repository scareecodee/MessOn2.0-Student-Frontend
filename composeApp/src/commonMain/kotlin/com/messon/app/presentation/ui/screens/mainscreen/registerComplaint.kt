package com.messon.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.app.presentation.ui.components.TextFieldItem
import com.messon.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import org.jetbrains.compose.resources.Font
import tech.annexflow.constraintlayout.compose.ConstraintLayout
import tech.annexflow.constraintlayout.compose.Dimension

@Composable
fun RegisterComplaintScreen(
    modifier: Modifier = Modifier,
    heading: String,
    subHeading: String,
    onSendClick: () -> Unit,
    limit: Int = 1000
) {
    var complaint by remember { mutableStateOf("") }
    val currentComplaintLength = complaint.length

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (statusBarBox, headingRef, subHeadingRef, listRef) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateTopPadding() + 5.dp
                )
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = heading,
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            color = CustomColors.RichCerulean,
            modifier = Modifier
                .constrainAs(headingRef) {
                    top.linkTo(statusBarBox.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = 15.dp)
        )

        Text(
            text = subHeading,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.opensansmedium)),
            color = Color.LightGray,
            modifier = Modifier
                .constrainAs(subHeadingRef) {
                    top.linkTo(headingRef.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }
                .padding(vertical = 1.dp)
                .border(1.dp, color = Color.Red)
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(listRef) {
                    top.linkTo(subHeadingRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 16.dp)
            ,verticalArrangement = Arrangement.Top
        ) {
            item {
                TextFieldItem(
                    singleLine = false,
                    modifier = Modifier,
                    heading = "Complaint",
                    value = complaint,
                    headingColor = CustomColors.BlackberryCream,
                    onValueChange = {
                        if (it.length <= limit) {
                            complaint = it
                        }
                    },
                    textFieldHeight = 150.dp,
                    limit = limit,
                    currentComplaintLength = currentComplaintLength
                )
            }

            item {
                Button(
                    onClick = onSendClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomColors.RichCerulean
                    )
                ) {
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