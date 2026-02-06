package com.messon.student.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.messon.student.app.domain.dataModels.User
import com.messon.student.app.presentation.ui.components.EachItemProfileScreen
import com.messon.student.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.intersemibold
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensansregular
import org.jetbrains.compose.resources.Font



@Composable
fun ProfileScreen(
    user: User,
    modifier: Modifier,
    onNotificationClick:()->Unit={},
    onAboutUsClick:()->Unit={},
    onLogoutClick:()->Unit={},
){

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
            .background(color = Color.White)
    ) {
        val (statusBarBox,headingRef,profileImageRef,itemListRef,nameRef,mailIdRef)=createRefs()
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
            text ="Profile",
            fontSize =20.sp,
            fontFamily = FontFamily(Font(Res.font.opensansmedium)),
            modifier = Modifier
                .constrainAs(headingRef){
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top, margin =60.dp)
                }
        )

        Box(
            modifier = Modifier.constrainAs(profileImageRef){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(headingRef.bottom, margin = 25.dp)
            }
                .background(color = Color.White, shape = CircleShape)
                .border(width = 2.dp, color = CustomColors.RichCerulean, shape = CircleShape)
        ){
            AsyncImage(
                model = user.profileImg,
//                error = painterResource(Res.drawable.error),
                contentDescription = "profile imagae",
                modifier = Modifier .padding(10.dp)
                    .size(80.dp)
                    .clip(CircleShape)

//                fallback = painterResource(Res.drawable.default_avatar),
              ,  contentScale = ContentScale.Crop
            )
        }


        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = user.name?:"",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(Res.font.intersemibold)),
            modifier= Modifier.constrainAs(nameRef){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(profileImageRef.bottom, margin = 15.dp)
            }
                .padding(horizontal = 20.dp)
        )


        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = user.mailId,
            fontSize = 12.sp,
            color = Color.LightGray,
            fontFamily = FontFamily(Font(Res.font.intersemibold)),
            modifier= Modifier.constrainAs(mailIdRef){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(nameRef.bottom, margin = 5.dp)
            }
                .padding(horizontal = 20.dp)
        )


        LazyColumn(
            modifier = Modifier.constrainAs(itemListRef){
                top.linkTo(mailIdRef.bottom, margin = 20.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width= Dimension.fillToConstraints
                height= Dimension.fillToConstraints
            }
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 30.dp)

        ) {
            item{
                Text(
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.opensansregular)),
                    text = "Settings",
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                )
            }


            item {
                EachItemProfileScreen(
                    icon = Icons.Outlined.Notifications,
                    title = "Notifications",
                    onClick = onNotificationClick
                )
            }

            item{
                Text(
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.opensansregular)),
                    text = "Others",
                    modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                )
            }


            item {
                EachItemProfileScreen(
                    icon = Icons.Outlined.Info,
                    title = "About Us",
                    onClick = onAboutUsClick
                )
            }

            item {
                EachItemProfileScreen(
                    icon = Icons.Outlined.Logout,
                    title = "Logout",
                    onClick = onLogoutClick
                )
            }

        }
    }
}