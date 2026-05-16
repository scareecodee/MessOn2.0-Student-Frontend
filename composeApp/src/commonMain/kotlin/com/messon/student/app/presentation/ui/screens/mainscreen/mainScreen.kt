package com.messon.student.app.presentation.ui.screens.mainscreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.dataModels.NavItem
import com.messon.student.app.domain.dataModels.User
import com.messon.student.app.presentation.ui.screens.mess.ViewMessMenuScreen
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.Screen
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.home
import messon.composeapp.generated.resources.menu
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.person
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(
    navController: NavController,
    authSecureStorage: AuthSecureStorage,
    couponViewModel: CouponViewModel,
    authViewModel: AuthViewModel,
    menuFetchVM: MenuFetchVM
){
    var selectedItemIndex by remember { mutableStateOf(0) }
    val navItemList=listOf(
        NavItem(icon = Res.drawable.home,"Home"),
        NavItem(icon = Res.drawable.menu,"Menu"),
        NavItem(icon = Res.drawable.person,"Profile")
    )
    Scaffold(
        modifier= Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                tonalElevation =20.dp,
                modifier = Modifier.height(85.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart =20.dp, topEnd = 20.dp)
                    )
                    .shadow(
                        elevation =20.dp,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                ,
                containerColor = Color.White
            ) {
                navItemList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = CustomColors.BlackberryCream,
                            selectedTextColor = CustomColors.BlackberryCream,
                            selectedIndicatorColor =Color(0xFFF7E6ED),
                                    unselectedIconColor = Color.Black,
                                    unselectedTextColor = Color.Black,
                            disabledIconColor = Color.Black,
                            disabledTextColor = Color.Black

                        ),
                        onClick= {
                            selectedItemIndex=index
                        },
                        selected = selectedItemIndex==index,
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(Res.font.opensansmedium) )
                            )
                        }
                    )
                }
            }
        }

    ) {innerPadding->
        ContentScreen(
            modifier = Modifier.padding(top = 0.dp, bottom = innerPadding.calculateBottomPadding()),
            selectedScreenIndex=selectedItemIndex,
            navController=navController,
            authSecureStorage =authSecureStorage,
            couponViewModel = couponViewModel,
            authViewModel = authViewModel,
            menuFetchVM=menuFetchVM
        )
    }
}


@Composable
fun ContentScreen(
    modifier: Modifier,
    selectedScreenIndex: Int,
    navController: NavController,
    authSecureStorage: AuthSecureStorage,
    couponViewModel: CouponViewModel,
    authViewModel: AuthViewModel,
    menuFetchVM: MenuFetchVM
) {
    var name by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf<String?>(null) }
    var profileImg by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        name = authSecureStorage.getName()
        email = authSecureStorage.getEmail()
        profileImg = authSecureStorage.getPicture()
    }

    when(selectedScreenIndex){
        0 -> HomeScreen(
            modifier = modifier,
            onBellIconClick = {
                navController.navigate(Screen.NotificationScreen.route){
                    launchSingleTop = true
                }
            },
            onComplaintClick ={
                navController.navigate(Screen.ViewComplaintsScreen.route){
                    launchSingleTop = true
                }
            },
            couponViewModel = couponViewModel,
            authSecureStorage = authSecureStorage,
           onMealPlannerClick = {
               navController.navigate(Screen.MealPreferenceScreen.route){
                   launchSingleTop = true
               }
           }
        )
        1 -> ViewMessMenuScreen(
            onBackBtnClick = {navController.popBackStack()},
            menuFetchVM =menuFetchVM
        )
        2 -> ProfileScreen(
            modifier = modifier,
            user = User(
                name = name ?: "",
                mailId = email ?: "",
                profileImg = profileImg ?: "",
                id = ""
            ),
            onLogoutClick = {
                authViewModel.logout{
                }

            }

        )
    }
}
