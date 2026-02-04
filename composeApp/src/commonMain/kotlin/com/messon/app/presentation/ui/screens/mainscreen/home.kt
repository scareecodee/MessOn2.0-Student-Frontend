package com.messon.app.presentation.ui.screens.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messon.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font
import tech.annexflow.constraintlayout.compose.ConstraintLayout
import tech.annexflow.constraintlayout.compose.Dimension
import co.touchlab.kermit.Logger
import com.messon.app.presentation.ui.components.AnalyticsTab
import com.messon.app.presentation.ui.components.CardHomeScreen
import com.messon.app.presentation.ui.components.ComplaintBox
import com.messon.app.presentation.ui.components.CouponCard
import com.messon.app.presentation.viewmodel.CouponViewModel
import com.messon.app.utils.CouponState
import com.messon.app.utils.getCurrentDate
import com.messon.app.utils.getCurrentDay
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.ToastGravity
import multiplatform.network.cmptoast.showToast


@Composable
fun HomeScreen(
    modifier: Modifier,
    onBellIconClick:()-> Unit,
    studentName:String,
    onComplaintClick:()->Unit,
    couponViewModel: CouponViewModel
){

    var clickedCardIndex by remember{ mutableIntStateOf(-1) }
    val uiState by couponViewModel.uiState.collectAsStateWithLifecycle()
    val dayWithDate = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = CustomColors.RichCerulean,
                fontSize = 23.5.sp
            )
        ) {
            append(getCurrentDay())
            append(",")
        }

        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontSize = 14.5.sp
            )
        ) {
            append(getCurrentDate())
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is CouponState.Error) {
            clickedCardIndex=-1
            showToast((uiState as CouponState.Error).message)
            couponViewModel.clearError()
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        val screenHeight=maxHeight
        ConstraintLayout(
            modifier = modifier.fillMaxSize()
                .blur(if (clickedCardIndex != -1) 2.5.dp else 0.dp)

        ){
            val (coloredBoxRef,whiteBoxRef,greetingsRef,bellIconRef,lazyColumnRef,todaysDateRef)=createRefs()


            Box(
                modifier = Modifier.constrainAs(coloredBoxRef){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        color = CustomColors.LightGrayBlue,
                    )
            )

            Box(
                modifier = Modifier.constrainAs(bellIconRef){
                    top.linkTo(parent.top, margin =60.dp)
                    end.linkTo(parent.end, margin = 25.dp)
                }
                    .size(30.dp)
                    .background(
                        color = Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable(
                        enabled = clickedCardIndex==-1
                    ){
                        onBellIconClick()
                    }
            ){
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Bell icon",
                    modifier = Modifier.padding(5.dp)
                        .size(22.dp)
                )
            }
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text ="Hey, $studentName \uD83D\uDC4B Have a great meal!",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                modifier = Modifier.constrainAs(greetingsRef) {
                    start.linkTo(parent.start, margin = 30.dp)
                    end.linkTo(bellIconRef.start, margin = 25.dp)
                    top.linkTo(bellIconRef.top)
                    bottom.linkTo(bellIconRef.bottom)
                    width = Dimension.fillToConstraints
                }
            )

            Box(
                modifier = Modifier.constrainAs(whiteBoxRef){
                    top.linkTo(parent.top, margin = 110.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height= Dimension.fillToConstraints
                }
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topEnd =30.dp,
                            topStart = 30.dp
                        )
                    )
            )

            Text(
                text =dayWithDate ,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                modifier = Modifier.constrainAs(todaysDateRef){
                    start.linkTo(parent.start, margin = 20.dp)
                    top.linkTo(whiteBoxRef.top)
                }
                    .padding(top = 28.dp, bottom = 20.dp, end = 10.dp)

            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                userScrollEnabled = clickedCardIndex == -1,
                modifier = Modifier.fillMaxWidth()
                    .constrainAs(lazyColumnRef){
                        top.linkTo(todaysDateRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height= Dimension.fillToConstraints
                    }
            ) {
                item {

                    Text(
                        text ="Today’s Coupons",
                        fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Text(
                        text = "Click on the coupons to get QR",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.opensansregular)),
                        color = Color.LightGray,
                        modifier = Modifier.padding(start = 20.dp)

                    )
                    Spacer(modifier = Modifier.height(10.dp))

                }
                item {
                    LazyRow(
                        userScrollEnabled = clickedCardIndex == -1,
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                    ) {
                        item {
                            CardHomeScreen(
                                modifier = Modifier.padding(start = 15.dp),
                                borderColor = CustomColors.RichCerulean,
                                containerColor = Color(0xFFF5F8FA),
                                mealSlot = "Breakfast",
                                onCardClick = {
                                    clickedCardIndex=0
                                   couponViewModel.onCouponClick(mealSlot ="breakfast")
                                }
                            )
                        }
                        item {
                            CardHomeScreen(
                                modifier = Modifier,
                                borderColor = CustomColors.HoneyBronze,
                                containerColor =Color(0xFFFFFBF4),
                                mealSlot = "Lunch",
                                onCardClick = {
                                    clickedCardIndex=1
                                    couponViewModel.onCouponClick(mealSlot ="lunch")
                                }
                            )
                        }
                        item {
                            CardHomeScreen(
                                modifier = Modifier.padding(end = 15.dp),
                                borderColor = CustomColors.BlackberryCream,
                                containerColor =Color(0xFFF6F5F7),
                                mealSlot = "Dinner",
                                onCardClick = {
                                    clickedCardIndex=2
                                    couponViewModel.onCouponClick(mealSlot ="dinner")
                                }
                            )
                        }
                    }
                }

                item {
                    Divider(
                        thickness = 1.5.dp,
                        color = CustomColors.OffWhite,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 40.dp)
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Analytics",
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold))

                        )
                        Text(
                            text = "This Week",
                            fontSize = 10.sp,
                            color = CustomColors.RichCerulean,
                            fontFamily = FontFamily(Font(Res.font.opensansregular))
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                }

                item {
                    AnalyticsTab(
                        isClickable =clickedCardIndex==-1,
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                    )
                }

                item {
                    ComplaintBox(
                        isClickable=clickedCardIndex == -1,
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 15.dp),
                        onComplaintClick = onComplaintClick
                    )
                }
            }
        }
        when (uiState) {

            CouponState.Idle -> Unit

            CouponState.Loading -> {
                CircularProgressIndicator()
            }

            is CouponState.Success -> {
                val data = (uiState as CouponState.Success).couponDetail.toString()
                AnimatedVisibility(
                    visible = clickedCardIndex != -1,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    CallCouponCard(
                        onCloseBtnClick = {
                            clickedCardIndex = -1
                                          },
                        data = data,
                        modifier = Modifier.height(screenHeight * 0.45f),
                        clickedCardIndex = clickedCardIndex,
                        currentDate = getCurrentDate()
                    )
                }
            }

            is CouponState.Error-> Unit
        }


    }
    }


@Composable
fun CallCouponCard(
    currentDate:String,
    onCloseBtnClick:()-> Unit,
    data: String,
    clickedCardIndex:Int=-1,
    modifier: Modifier
){
    when(clickedCardIndex){
        0->CouponCard(
            modifier = modifier,
            mealSlot = "Breakfast",
            date=currentDate,
            borderColor = CustomColors.RichCerulean,
            containerColor = Color(0xFFF5F8FA),
            data =data,
            onCloseBtnClick = onCloseBtnClick
            )
        1-> CouponCard(
            modifier = modifier,
            mealSlot = "Lunch",
            date=currentDate,
            borderColor = CustomColors.HoneyBronze,
            containerColor = Color(0xFFFFFBF4),
            data =data,
            onCloseBtnClick = onCloseBtnClick
        )
        2->
            CouponCard(
                modifier = modifier,
                mealSlot = "Dinner",
                date=currentDate,
                borderColor = CustomColors.BlackberryCream,
                containerColor =Color(0xFFF6F5F7),
                data =data,
                onCloseBtnClick = onCloseBtnClick
            )
    }
}