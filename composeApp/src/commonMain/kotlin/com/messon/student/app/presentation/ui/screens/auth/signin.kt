package com.messon.student.app.presentation.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.domain.dataModels.NotificationState
import com.messon.student.app.domain.provider.DeviceInfoProvider
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.ui.state.AuthState
import com.messon.student.app.presentation.viewmodel.NotificationViewModel
import com.messon.student.composeapp.generated.resources.Res
import com.messon.student.composeapp.generated.resources.google
import org.jetbrains.compose.resources.Font
import com.messon.student.composeapp.generated.resources.opensansbold
import com.messon.student.composeapp.generated.resources.opensansmedium
import com.messon.student.composeapp.generated.resources.opensansregular
import multiplatform.network.cmptoast.ToastGravity
import multiplatform.network.cmptoast.ToastPadding
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onBtnClick: () -> Unit,
    isCircularProgressVisible: Boolean
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (statusBarBox, illustrationRef, headingRef, subtitleRef, googleBtn, textRef, circularProgressOverlay) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MaterialTheme.colorScheme.background)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Box(
            modifier = Modifier
                .size(160.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                    shape = RoundedCornerShape(80.dp)
                )
                .constrainAs(illustrationRef) {
                    top.linkTo(statusBarBox.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = "🍱",
                fontSize = 56.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "Login to MessOn",
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(headingRef) {
                    top.linkTo(illustrationRef.bottom, margin = 28.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = "Your daily campus mess companion",
            fontFamily = FontFamily(Font(Res.font.opensansregular)),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(subtitleRef) {
                    top.linkTo(headingRef.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Button(
            onClick = { onBtnClick() },
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 4.dp
            ),
            modifier = Modifier
                .constrainAs(googleBtn) {
                    top.linkTo(subtitleRef.bottom, margin = 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(28.dp)
                )
        ) {
            Icon(
                painter = painterResource(Res.drawable.google),
                contentDescription = "Google Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Continue with Google",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = "By continuing, you agree to our User Agreement and acknowledge that you understand the Privacy Policy",
            fontFamily = FontFamily(Font(Res.font.opensansregular)),
            fontSize = 11.sp,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f), // High readability fallback
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(textRef) {
                    bottom.linkTo(parent.bottom, margin = 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 40.dp)
        )

        if (isCircularProgressVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
                    .clickable(enabled = false) {}
                    .constrainAs(circularProgressOverlay) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.size(90.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp,
                            strokeCap = StrokeCap.Round,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SignInRoute(
    googleAuth: GoogleAuth,
    viewModel: AuthViewModel,
    notificationViewModel: NotificationViewModel,
    deviceInfoProvider: DeviceInfoProvider,
    onLoginSuccess: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()
    val notificationState by notificationViewModel.uiState.collectAsState()
    val isVisible = state is AuthState.Loading

    SignInScreen(
        onBtnClick = {
            viewModel.onGoogleClick(googleAuth = googleAuth)
        },
        isCircularProgressVisible = isVisible
    )

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            val user = (state as AuthState.Success).user
            val deviceInfo = deviceInfoProvider.getDeviceInfo()
            notificationViewModel.onLogin(deviceInfo)
        }
        if (state is AuthState.Error) {
            showToast(
                textSize = 14.sp,
                message = (state as AuthState.Error).message,
                backgroundColor = Color.DarkGray,
                textColor = Color.White,
                gravity = ToastGravity.Top,
                padding = ToastPadding(12),
            )
        }
    }

    LaunchedEffect(notificationState.notificationState) {
        when (val ns = notificationState.notificationState) {
            is NotificationState.Registered -> {
                onLoginSuccess()
            }
            is NotificationState.PermissionDenied -> {
                onLoginSuccess()
            }
            is NotificationState.Error -> {
                onLoginSuccess()
            }
            else -> Unit
        }
    }
}