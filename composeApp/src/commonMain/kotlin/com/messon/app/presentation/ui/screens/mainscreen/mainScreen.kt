package com.messon.app.ui.screens.mainscreen

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
import com.messon.app.domain.dataModels.NavItem
import com.messon.app.domain.dataModels.User
import com.messon.app.theme.CustomColors
import com.messon.app.utils.Screen
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.home
import messon.composeapp.generated.resources.menu
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.person
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(
    navController: NavController
){
    var selectedItemIndex by remember { mutableStateOf(0) }
    val navItemList=listOf<NavItem>(
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
            navController=navController
        )
    }
}


@Composable
fun ContentScreen(
    modifier: Modifier,
    selectedScreenIndex: Int,
    navController: NavController
){
    when(selectedScreenIndex){
        0->HomeScreen(
            modifier = modifier,
            onBellIconClick = {},
            studentName = "Akash",
            onComplaintClick ={
                navController.navigate(Screen.RegisterComplainScreen.route){
                    launchSingleTop=true
                }
            }
        )
        1->MenuScreen(
            modifier = modifier
        )
        2->ProfileScreen(
            modifier = modifier,
            user = User(
                name = "Sundram Kumar",
                mailId = "abc@gmail.com",
                profileImg = "jnjkdjdnj"
            )
        )
    }
}