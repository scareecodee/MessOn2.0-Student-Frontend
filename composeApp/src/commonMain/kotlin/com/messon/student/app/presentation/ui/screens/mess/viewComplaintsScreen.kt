package com.messon.student.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.domain.dataModels.Complaint
import com.messon.student.app.presentation.ui.components.ComplaintCard
import com.messon.student.app.presentation.ui.components.SearchBar
import com.messon.student.app.theme.CustomColors
import com.messon.student.app.theme.CustomColors.BlackberryCream
import com.messon.student.app.theme.CustomColors.OffWhite
import com.messon.student.app.theme.CustomColors.RichCerulean
import com.messon.student.app.utils.ComplaintStatus
import com.messon.student.app.utils.UserInteraction
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansmedium
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font


val INITIAL_COMPLAINTS = listOf(
    Complaint(
        "1",
        "Arjun Sharma",
        "STU-2023-042",
        "Broken WiFi in Block B",
        "The WiFi connection in the south wing of Block B library has been unstable for the last 3 days.",
        ComplaintStatus.IN_PROGRESS,
        "Oct 24, 2023",
        42,
        2
    ),
    Complaint("2", "Priya V.", "STU-2023-118", "Canteen Food Quality", "The lunch served today was undercooked. This is the second time this week.", ComplaintStatus.PENDING, "Oct 25, 2023", 128, 5),
    Complaint("3", "Rohan Das", "STU-2023-089", "New Sports Equipment", "Most of the basketballs in the gym are worn out. Requesting management for new gear.", ComplaintStatus.RESOLVED, "Oct 20, 2023", 85, 12),
    Complaint("4", "Sneha Kapur", "STU-2023-201", "Washroom Hygiene", "The washrooms on the 3rd floor of the Science Block need immediate cleaning.", ComplaintStatus.PENDING, "Oct 25, 2023", 56, 1)
)

@Composable
fun ViewComplaintsScreen(
    onRegisterCompClick:()-> Unit,
    onBackClick:()-> Unit
) {
    var complaints by remember { mutableStateOf(INITIAL_COMPLAINTS) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(ComplaintStatus.ALL) }

    val filteredComplaints = complaints.filter {
        (it.title.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)) &&
                (selectedFilter == ComplaintStatus.ALL || it.status == selectedFilter)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        val (header, fab,list,statusBarBox,backBtnRef,headingRef,searchBarRef,filtersRef) = createRefs()
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
            text = "Complaints",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

            SearchBar(
                placeholderText = "Search complaint",
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                }
                ,
                modifier = Modifier.constrainAs(searchBarRef) {
                    top.linkTo(backBtnRef.bottom, 10.dp)
                    start.linkTo(parent.start, 5.dp)
                    end.linkTo(parent.end, 5.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier
                    .constrainAs(filtersRef){
                        top.linkTo(searchBarRef.bottom, margin = 12.dp)
                        centerHorizontallyTo(searchBarRef)
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ComplaintStatus.entries) { status ->
                    FilterChip(
                        selected = selectedFilter == status,
                        onClick = { selectedFilter = status },
                        label = {
                            Text(
                                text = status.label,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(
                                    Font(Res.font.opensanssemiBold)
                                )
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BlackberryCream,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = BlackberryCream
                        ),
                        border = null,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(36.dp)
                    )
                }
            }


        LazyColumn(
            modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(filtersRef.bottom, margin = 14.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(filteredComplaints) { complaint ->
                ComplaintCard(
                    complaint = complaint,
                    onLikeClick = {
                        complaints = complaints.map {
                            if (it.id == complaint.id) {
                                val newInteraction = if (it.userInteraction == UserInteraction.LIKE) null else UserInteraction.LIKE
                                val likeDiff = if (it.userInteraction == UserInteraction.DISLIKE) -1 else if (it.userInteraction == UserInteraction.DISLIKE) 1 else 1
                                val dislikeDiff = if (it.userInteraction == UserInteraction.DISLIKE) -1 else 0
                                it.copy(likes = it.likes + likeDiff, dislikes = it.dislikes + dislikeDiff, userInteraction = newInteraction)
                            } else it
                        }
                    },
                    onDislikeClick = {
                        complaints = complaints.map {
                            if (it.id == complaint.id) {
                                val newInteraction = if (it.userInteraction == UserInteraction.DISLIKE) null else UserInteraction.DISLIKE
                                val dislikeDiff = if (it.userInteraction == UserInteraction.DISLIKE) -1 else if (it.userInteraction == UserInteraction.LIKE) 1 else 1
                                val likeDiff = if (it.userInteraction == UserInteraction.LIKE) -1 else 0
                                it.copy(dislikes = it.dislikes + dislikeDiff, likes = it.likes + likeDiff, userInteraction = newInteraction)
                            } else it
                        }
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                onRegisterCompClick()
            },
            containerColor = RichCerulean,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .size(58.dp)
                .constrainAs(fab) {
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                }
                .shadow(12.dp, CircleShape)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Complaint", modifier = Modifier.size(32.dp))
        }
    }
}
