package com.messon.student.app.presentation.ui.screens.mess
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.domain.dataModels.ComplaintCategory
import com.messon.student.app.domain.dataModels.ComplaintHistory
import com.messon.student.app.presentation.ui.components.CategoryCard
import com.messon.student.app.presentation.ui.components.HistoryItem
import com.messon.student.app.theme.CustomColors
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun RegisterComplaintScreen(
    onBackClick:()-> Unit
) {
    var description by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf("food") }

    val categories = listOf(
        ComplaintCategory("food", "Food Quality", Icons.Default.Restaurant),
        ComplaintCategory("hygiene", "Hygiene", Icons.Default.CleanHands),
        ComplaintCategory("service", "Staff Behavior", Icons.Default.Person),
        ComplaintCategory("infra", "Infrastructure", Icons.Default.Settings)
    )

    val history = listOf(
        ComplaintHistory(1, "Food Quality", "Today, 12:45 PM", "In Review", "The lentils were undercooked."),
        ComplaintHistory(2, "Hygiene", "Yesterday", "Closed", "Handwash dispenser is empty.")
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.OffWhite)
    ) {
        val (lazyColumnRef,statusBarBox,backBtnRef,headingRef) = createRefs()
        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(CustomColors.LightGrayBlue)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )


        IconButton(
            onClick = {
                onBackClick()
            },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, 10.dp)
            }
        ) {
            Icon(Icons.Default.ChevronLeft, contentDescription = "back", modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = "Register Complaint",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )
        LazyColumn(
            modifier = Modifier.constrainAs(lazyColumnRef) {
                top.linkTo(headingRef.bottom, margin = 15.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color.White)
                        .padding(24.dp)
                ) {
                    val (catTitle, catGrid, descTitle, descBox, submitBtn) = createRefs()

                    Text(
                        text = "Select Category",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                            color = CustomColors.LightGrayBlue
                        ),
                        modifier = Modifier.constrainAs(catTitle) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(catGrid) {
                                top.linkTo(catTitle.bottom, margin = 16.dp)
                            },
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(categories.take(4)) { category ->

                            CategoryCard(
                                category = category,
                                isSelected = selectedCategoryId == category.id,
                                onClick = { selectedCategoryId = category.id },
                                modifier = Modifier
                            )
                        }
                    }

                    Text(
                        text = "Describe Issue",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                            color = CustomColors.LightGrayBlue
                        ),
                        modifier = Modifier.constrainAs(descTitle) {
                            top.linkTo(catGrid.bottom, margin = 32.dp)
                            start.linkTo(parent.start)
                        }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .constrainAs(descBox) {
                                top.linkTo(descTitle.bottom, margin = 16.dp)
                            }
                            .clip(RoundedCornerShape(20.dp))
                            .background(CustomColors.LightBlue.copy(alpha = 0.3f))
                            .border(1.dp, CustomColors.LightGrayBlue.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                            .padding(16.dp)
                    ) {
                        BasicTextField(
                            value = description,
                            onValueChange = { if (it.length <= 500) description = it },
                            modifier = Modifier.fillMaxSize(),
                            textStyle = TextStyle(color = CustomColors.BlackberryCream, fontSize = 16.sp,
                                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                            ),
                            decorationBox = { innerTextField ->
                                if (description.isEmpty()) {
                                    Text("Be specific about the issue...",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }

                    Button(
                        onClick = { /* Handle Submit */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .constrainAs(submitBtn) {
                                top.linkTo(descBox.bottom, margin = 24.dp)
                                bottom.linkTo(parent.bottom)
                            },
                        colors = ButtonDefaults.buttonColors(containerColor = CustomColors.RichCerulean),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("File Complaint", fontSize = 14.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),)
                    }
                }
            }

            item {
                Spacer(Modifier.height(32.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.History, contentDescription = null, tint = CustomColors.LightGrayBlue)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Previous Complaints",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                            color = CustomColors.LightGrayBlue,
                        )
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            items(history) { item ->
                HistoryItem(item)
            }
        }
    }
}
