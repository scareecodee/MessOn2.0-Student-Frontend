package com.messon.student.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
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
import com.messon.student.app.utils.Screen
import kotlinx.coroutines.launch
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
) {
    // Standard pages mapping (0: Home, 1: Menu, 2: Profile)
    val pageCount = 3
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()

    val navItemList = listOf(
        NavItem(icon = Res.drawable.home, "Home"),
        NavItem(icon = Res.drawable.menu, "Menu"),
        NavItem(icon = Res.drawable.person, "Profile")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end=24.dp,bottom = 16.dp) // Added floating offsets on all edges
                    .height(70.dp) // Sleeker height footprint for a lightweight floating pill feel
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(28.dp) // Fully rounded corner capsule shape
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f), // Adaptive glass boundary outline
                        shape = RoundedCornerShape(28.dp)
                    )
                    .clip(RoundedCornerShape(28.dp)),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                navItemList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), // Safe dynamic pastel highlight capsule
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selected = pagerState.currentPage == index,
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(Res.font.opensansmedium))
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.fillMaxSize(),
            bottomPadding = innerPadding.calculateBottomPadding(),
            pagerState = pagerState,
            navController = navController,
            authSecureStorage = authSecureStorage,
            couponViewModel = couponViewModel,
            authViewModel = authViewModel,
            menuFetchVM = menuFetchVM
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentScreen(
    modifier: Modifier,
    bottomPadding: Dp,
    pagerState: androidx.compose.foundation.pager.PagerState,
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

    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            when (pageIndex) {
                0 -> HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    onBellIconClick = {
                        navController.navigate(Screen.NotificationScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    onComplaintClick = {
                        navController.navigate(Screen.ViewComplaintsScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    couponViewModel = couponViewModel,
                    authSecureStorage = authSecureStorage,
                    onMealPlannerClick = {
                        navController.navigate(Screen.MealPreferenceScreen.route) {
                            launchSingleTop = true
                        }
                    }
                )
                1 -> ViewMessMenuScreen(
                    onBackBtnClick = {
                        if (!navController.popBackStack()) {
                            // Fallback pop
                        }
                    },
                    menuFetchVM = menuFetchVM
                )
                2 -> ProfileScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = bottomPadding), // Non-scrollable Profile gets standard padding
                    user = User(
                        name = name ?: "",
                        mailId = email ?: "",
                        profileImg = profileImg ?: "",
                        id = ""
                    ),
                    onLogoutClick = {
                        authViewModel.logout {}
                    }
                )
            }
        }
    }
}