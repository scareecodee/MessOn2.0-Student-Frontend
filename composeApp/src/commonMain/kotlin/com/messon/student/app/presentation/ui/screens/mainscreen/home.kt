package com.messon.student.app.presentation.ui.screens.mainscreen
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font
import com.messon.student.app.presentation.ui.components.AnalyticsTab
import com.messon.student.app.presentation.ui.components.CardHomeScreen
import com.messon.student.app.presentation.ui.components.CouponCard
import com.messon.student.app.presentation.ui.components.QuickAccessBox
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.ui.state.CouponState
import com.messon.student.app.presentation.ui.state.FetchMenuState
import com.messon.student.app.utils.MealSlot
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import com.messon.student.app.utils.getCurrentDate
import com.messon.student.app.utils.getCurrentDay
import multiplatform.network.cmptoast.showToast
import kotlinx.serialization.json.Json
import messon.composeapp.generated.resources.preference
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    modifier: Modifier,
    onBellIconClick:()-> Unit,
    onComplaintClick:()->Unit,
    couponViewModel: CouponViewModel,
    menuFetchVM: MenuFetchVM,
    authSecureStorage: AuthSecureStorage,
    onMealPlannerClick:()->Unit
){
    val pullToRefreshState = rememberPullToRefreshState()

    var userName by remember { mutableStateOf("") }
    val menuState by menuFetchVM.uiState.collectAsStateWithLifecycle()
    val isRefreshing = menuState is FetchMenuState.Loading
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
    LaunchedEffect(Unit){
        userName=authSecureStorage.getName()?:""
    }

    LaunchedEffect(uiState) {
        if (uiState is CouponState.Error) {
            clickedCardIndex=-1
            showToast((uiState as CouponState.Error).message)
            couponViewModel.clearError()
        }
    }



//    LaunchedEffect(menuState) {
//
//        if (menuState is MessMenuState.Error) {
//            showToast((menuState as MessMenuState.Error).message)
//            messMenuViewModel.clearCurrentDayMenuError()
//        }
//    }



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
                text ="Hey, $userName \uD83D\uDC4B Have a great meal!",
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

            PullToRefreshBox(
                state = pullToRefreshState,
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        containerColor = CustomColors.RichCerulean,
                        color = Color.White,
                        isRefreshing = isRefreshing,
                        state = pullToRefreshState
                    )
                },
                isRefreshing = isRefreshing,
                onRefresh = {
//                    messMenuViewModel.onMenuCardClick(
//                        getCurrentDay().lowercase(), currentDay = true
//                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(lazyColumnRef){
                        top.linkTo(todaysDateRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height= Dimension.fillToConstraints
                    },
                contentAlignment = Alignment.TopCenter
            ){
                LazyColumn(
                    userScrollEnabled = clickedCardIndex == -1,
                    modifier = Modifier.fillMaxWidth()

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
                                    fetchMenuState = menuState,
                                    modifier = Modifier.padding(start = 15.dp),
                                    borderColor = CustomColors.RichCerulean,
                                    containerColor = Color(0xFFF5F8FA),
                                    mealSlot = MealSlot.BREAKFAST,
                                    onCardClick = {
                                        clickedCardIndex=0
                                        couponViewModel.onCouponClick(mealSlot = MealSlot.BREAKFAST.value)
                                    }
                                )
                            }
                            item {
                                CardHomeScreen(
                                    fetchMenuState = menuState,
                                    modifier = Modifier,
                                    borderColor = CustomColors.HoneyBronze,
                                    containerColor =Color(0xFFFFFBF4),
                                    mealSlot = MealSlot.LUNCH,
                                    onCardClick = {
                                        clickedCardIndex=1
                                        couponViewModel.onCouponClick(mealSlot = MealSlot.LUNCH.value)
                                    }
                                )
                            }
                            item {
                                CardHomeScreen(
                                    fetchMenuState = menuState,
                                    modifier = Modifier.padding(end = 15.dp),
                                    borderColor = CustomColors.BlackberryCream,
                                    containerColor =Color(0xFFF6F5F7),
                                    mealSlot = MealSlot.DINNER,
                                    onCardClick = {
                                        clickedCardIndex=2
                                        couponViewModel.onCouponClick(mealSlot = MealSlot.DINNER.value)
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
                        QuickAccessBox(
                            icon = rememberVectorPainter(Icons.Outlined.Mood),
                            title = "Feedback",
                            isClickable=clickedCardIndex == -1,
                            modifier = Modifier
                                .padding(vertical = 10.dp, horizontal = 15.dp),
                            onClick = onComplaintClick
                        )
                    }

                    item {
                        QuickAccessBox(
                            icon = painterResource(Res.drawable.preference),
                            title = "Meal Planner",
                            isClickable=clickedCardIndex == -1,
                            modifier = Modifier
                                .padding(vertical = 10.dp, horizontal = 15.dp),
                            onClick = onMealPlannerClick
                        )
                    }
                }
            }

        }
        when (uiState) {

            CouponState.Idle -> Unit

            CouponState.Loading -> {
                CircularProgressIndicator()
            }

            is CouponState.Success -> {
                val data = (uiState as CouponState.Success).couponDetail
                val dataAsJson=Json.encodeToString(data)
                AnimatedVisibility(
                    visible = clickedCardIndex != -1,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    CallCouponCard(
                        onCloseBtnClick = {
                            clickedCardIndex = -1
                                          },
                        data = dataAsJson,
                        modifier = Modifier.height(screenHeight * 0.45f),
                        clickedCardIndex = clickedCardIndex,
                        currentDate = getCurrentDate(),
                        mealType = data.mealType.toString()
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
    modifier: Modifier,
    mealType: String
){
    when(clickedCardIndex){
        0->CouponCard(
            modifier = modifier,
            mealSlot = "Breakfast",
            date=currentDate,
            borderColor = CustomColors.RichCerulean,
            containerColor = Color(0xFFF5F8FA),
            data =data,
            onCloseBtnClick = onCloseBtnClick,
            mealType = mealType
            )
        1-> CouponCard(
            modifier = modifier,
            mealSlot = "Lunch",
            date=currentDate,
            borderColor = CustomColors.HoneyBronze,
            containerColor = Color(0xFFFFFBF4),
            data =data,
            onCloseBtnClick = onCloseBtnClick,
            mealType = mealType
        )
        2->
            CouponCard(
                modifier = modifier,
                mealSlot = "Dinner",
                date=currentDate,
                borderColor = CustomColors.BlackberryCream,
                containerColor =Color(0xFFF6F5F7),
                data =data,
                onCloseBtnClick = onCloseBtnClick,
                mealType = mealType
            )
    }
}