package com.messon.student.app.presentation.ui.screens.mainscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.presentation.ui.components.StarRatingBar
import com.messon.student.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font


@Composable
fun RegisterComplaintScreen(
    modifier: Modifier = Modifier,
    heading: String,
    subHeading: String,
    onSendClick: () -> Unit,
    onBackBtnClick:()-> Unit,
    limit: Int = 1000
) {
    var complaint by remember { mutableStateOf("") }
    val currentFeedbackLength = complaint.length
    var rating by remember { mutableStateOf(0) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        val (backBtnRef,statusBarBox, headingRef, subHeadingRef, listRef,ratingCardRef) = createRefs()

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


        IconButton(
            shape = CircleShape,
            onClick = onBackBtnClick,
            modifier = Modifier.constrainAs(backBtnRef){
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ){
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            maxLines = 1,
            text =heading,
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            modifier = Modifier
                .constrainAs(headingRef){
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )


        LazyColumn(
            modifier = Modifier.constrainAs(listRef){
                top.linkTo(backBtnRef.bottom, margin = 15.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width= Dimension.fillToConstraints
            }
        ){
            item{
                ElevatedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .height(120.dp)
                ){

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "How was the food?",
                            fontFamily =FontFamily(Font(Res.font.opensanssemiBold)),
                            fontSize = 16.sp,
                            maxLines = 2,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.size(15.dp))
                        StarRatingBar(
                            rating = rating,
                            onRatingChanged = { rating = it },
                            maxStars =5,
                            filledColor = Color(0xFFFFC107),
                            outlineColor = Color.LightGray
                        )
                    }
                }
            }

//            item {
//                AnimatedContent(
//                    targetState =
//                ){
//
//                }
//            }
        }


    }
}