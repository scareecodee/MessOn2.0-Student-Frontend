package com.messon.student.app.presentation.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.utils.AuthState
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.google
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensansregular
import multiplatform.network.cmptoast.ToastGravity
import multiplatform.network.cmptoast.ToastPadding
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import tech.annexflow.constraintlayout.compose.ConstraintLayout
import tech.annexflow.constraintlayout.compose.Dimension

@Composable
fun SignInScreen(
    modifier: Modifier= Modifier,
    onBtnClick:()->Unit,
    isCircularProgressVisible: Boolean
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        val (statusBarBox, headingRef, googleBtn,textRef,circularProgress) = createRefs()


        Box(
            modifier = Modifier.fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )


        Text(
            color = CustomColors.RichCerulean ,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text ="Login to MessOn",
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            fontSize = 26.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
                .constrainAs(headingRef){
                    top.linkTo(parent.top, margin = 200.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Button(
            enabled = if(isCircularProgressVisible) false else true,
            modifier = Modifier.constrainAs(googleBtn){
                top.linkTo(headingRef.bottom, margin =35.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                width= Dimension.fillToConstraints
            }.height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .border(1.dp,Color.LightGray, shape =RoundedCornerShape(50.dp))
            ,
            onClick = onBtnClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Icon(
                painter = painterResource(Res.drawable.google),
                contentDescription = "login",
                tint=Color.Unspecified,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text="Continue With Google",
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.opensansmedium)),
            )
        }

        Text(
            text ="By continuing, you agree to our User Agreement and acknowledge that you understand the Privacy Policy",
            fontFamily = FontFamily(Font(Res.font.opensansregular)),
            fontSize = 12.sp,
            color = Color.LightGray,
            modifier = Modifier
                .constrainAs(textRef){
                    bottom.linkTo(parent.bottom, margin = 100.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 20.dp)
        )

        if(isCircularProgressVisible){
            CircularProgressIndicator(
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = CustomColors.RichCerulean,
                modifier = Modifier.size(50.dp)
                    .constrainAs(circularProgress){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

@Composable
fun SignInRoute(
    googleAuth: GoogleAuth,
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState.value


    isVisible = state is AuthState.Loading

    SignInScreen(
        onBtnClick = {
            viewModel.onGoogleClick(googleAuth = googleAuth)
                     },
        isCircularProgressVisible = isVisible
    )

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            onLoginSuccess()
        }
        if (state is AuthState.Error) {
            showToast(
                textSize = 20.sp,
                message = state.message,
                backgroundColor = Color.LightGray,
                textColor = CustomColors.RichCerulean,
                gravity = ToastGravity.Top,
                padding = ToastPadding(12),
            )
        }
    }
}

