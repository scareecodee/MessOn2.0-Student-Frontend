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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.messon.student.app.domain.dataModels.ComplaintCategory
import com.messon.student.app.domain.dataModels.ComplaintHistory
import com.messon.student.app.presentation.ui.components.CategoryCard
import com.messon.student.app.presentation.ui.components.HistoryItem
import com.messon.student.app.presentation.ui.state.CreateComplaintState
import com.messon.student.app.presentation.viewmodel.ComplaintVM
import com.messon.student.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font
import com.messon.student.composeapp.generated.resources.opensansbold
import com.messon.student.composeapp.generated.resources.opensansmedium
import com.messon.student.composeapp.generated.resources.opensansregular
import com.messon.student.composeapp.generated.resources.opensanssemiBold

@Composable
fun RegisterComplaintScreen(
    onBackClick: () -> Unit,
    complaintVM: ComplaintVM
) {

    val saveState by complaintVM.uiState.collectAsStateWithLifecycle()
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember {
        mutableStateOf(com.messon.student.app.utils.ComplaintCategory.FOOD)
    }
    val history = listOf(
        ComplaintHistory(1, "Food Quality", "Today, 12:45 PM", "In Review", "The lentils were undercooked."),
        ComplaintHistory(2, "Hygiene", "Yesterday", "Closed", "Handwash dispenser is empty.")
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (lazyColumnRef, statusBarBox, backBtnRef, headingRef) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MaterialTheme.colorScheme.background)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, 10.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "back",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Register Complaint",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            color = MaterialTheme.colorScheme.onBackground,
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
                        .background(MaterialTheme.colorScheme.surface) // Adaptive card surfaces
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f), RoundedCornerShape(32.dp))
                        .padding(24.dp)
                ) {
                    val (catTitle, catGrid, descTitle, descBox, submitBtn) = createRefs()

                    Text(
                        text = "Select Category",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                        items(com.messon.student.app.utils.ComplaintCategory.entries) { category ->
                            CategoryCard(
                                category = ComplaintCategory(
                                    id = category.id,
                                    label = category.label,
                                    icon = category.icon
                                ),
                                isSelected = selectedCategory == category,
                                onClick = { selectedCategory = category },
                                modifier = Modifier
                            )
                        }
                    }

                    Text(
                        text = "Describe Issue",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f)) // Muted input backing
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                            .padding(16.dp)
                    ) {
                        BasicTextField(
                            value = description,
                            onValueChange = { if (it.length <= 500) description = it },
                            modifier = Modifier.fillMaxSize(),
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(Res.font.opensansregular)),
                            ),
                            decorationBox = { innerTextField ->
                                if (description.isEmpty()) {
                                    Text(
                                        text = "Be specific about the issue...",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(Res.font.opensansmedium)),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }

                    Button(
                        onClick = {
                            complaintVM.createComplaint(
                                subject =selectedCategory.label,
                                description = description
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .constrainAs(submitBtn) {
                                top.linkTo(descBox.bottom, margin = 24.dp)
                                bottom.linkTo(parent.bottom)
                            },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        if (saveState is CreateComplaintState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                text = "File Complaint",
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(Res.font.opensanssemiBold))
                            )
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(32.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Previous Complaints",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                            color = MaterialTheme.colorScheme.onBackground,
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