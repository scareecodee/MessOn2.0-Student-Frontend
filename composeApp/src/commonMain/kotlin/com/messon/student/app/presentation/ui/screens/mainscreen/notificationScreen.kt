package com.messon.student.app.presentation.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.presentation.ui.components.EachNotificationItem
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import org.jetbrains.compose.resources.Font

@Composable
fun NotificationScreen(
    modifier: Modifier,
    onBackBtnClick: () -> Unit,
    onMarkAsReadBtnClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (statusBarBox, headingRef, listRef, backBtnRef, markAsReadBtn) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MaterialTheme.colorScheme.background)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        IconButton(
            shape = CircleShape,
            onClick = onBackBtnClick,
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, margin = 15.dp)
                top.linkTo(statusBarBox.bottom, margin = 10.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back btn",
                // Added explicit high contrast tint for proper dark/light rendering
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            maxLines = 1,
            text = "Notifications",
            fontSize = 22.sp,
            // Added explicit dynamic text color
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            modifier = Modifier
                .constrainAs(headingRef) {
                    start.linkTo(backBtnRef.end, margin = 5.dp)
                    top.linkTo(backBtnRef.top)
                    bottom.linkTo(backBtnRef.bottom)
                }
        )

        Button(
            colors = ButtonDefaults.buttonColors(
                // Adaptive secondary button styling
                containerColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            onClick = onMarkAsReadBtnClick,
            modifier = Modifier.constrainAs(markAsReadBtn) {
                end.linkTo(parent.end, margin = 18.dp) // Adjusted end margin for aligning perfectly with standard items
                top.linkTo(headingRef.bottom, margin = 10.dp)
            }
        ) {
            Text(
                text = "mark all as read",
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                fontSize = 11.sp,
                fontFamily = FontFamily(Font(Res.font.opensansmedium)),
            )
        }

        LazyColumn(
            modifier = Modifier.constrainAs(listRef) {
                top.linkTo(markAsReadBtn.bottom, margin = 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Perfectly matches professional item separation
        ) {
            items(7) {
                EachNotificationItem(
                    subject = "Today's Special! Today's Special! Today's Special!",
                    description = "Hyderabadi Chicken Biryani will be served for Sunday Lunch. Don't miss it!",
                    // Dynamically binds light and dark themed icon highlights
                    iconColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier,
                    createdTime = "10 min ago",
                    painter = rememberVectorPainter(Icons.Outlined.Fastfood),
                    contentDescription = "special meal highlight",
                )
            }
        }
    }
}