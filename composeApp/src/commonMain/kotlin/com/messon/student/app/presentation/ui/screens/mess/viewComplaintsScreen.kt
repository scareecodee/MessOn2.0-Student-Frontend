package com.messon.student.app.presentation.ui.screens.mess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.messon.student.app.presentation.ui.components.ComplaintCard
import com.messon.student.app.presentation.ui.components.SearchBar
import com.messon.student.app.presentation.viewmodel.FetchComplaintsVM
import com.messon.student.app.utils.ComplaintFilter
import com.messon.student.app.utils.toFilter
import kotlinx.coroutines.flow.distinctUntilChanged
import messon.composeapp.generated.resources.Res
import messon.composeapp.generated.resources.opensansbold
import messon.composeapp.generated.resources.opensansregular
import messon.composeapp.generated.resources.opensanssemiBold
import org.jetbrains.compose.resources.Font

@Composable
fun ViewComplaintsScreen(
    onRegisterCompClick: () -> Unit,
    onBackClick: () -> Unit,
    fetchComplaintsVM: FetchComplaintsVM
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember {
        mutableStateOf(ComplaintFilter.ALL)
    }

    val fetchComplaintState by fetchComplaintsVM.state.collectAsState()
    val isRefreshing: Boolean = fetchComplaintState.isRefreshing

    val filteredComplaints = remember(fetchComplaintState.complaints, searchQuery, selectedFilter) {
        fetchComplaintState.complaints.filter { complaint ->
            val matchesSearch = searchQuery.isEmpty() ||
                    complaint.title.contains(searchQuery, true) ||
                    complaint.description.contains(searchQuery, true)

            val matchesStatus = selectedFilter == ComplaintFilter.ALL ||
                    complaint.status.toFilter() == selectedFilter

            matchesSearch && matchesStatus
        }
    }

    LaunchedEffect(Unit) {
        fetchComplaintsVM.loadFirstPage()
    }

    LaunchedEffect(searchQuery) {
        fetchComplaintsVM.setSearch(searchQuery)
    }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState, fetchComplaintState) {
        snapshotFlow { lazyListState.layoutInfo }
            .distinctUntilChanged()
            .collect { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

                if (lastVisibleIndex >= totalItems - 3 &&
                    totalItems > 0 &&
                    !fetchComplaintState.isLoadingMore &&
                    !fetchComplaintState.endReached &&
                    fetchComplaintState.error == null
                ) {
                    fetchComplaintsVM.loadNextItems()
                }
            }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (fab, list, statusBarBox, backBtnRef, headingRef, searchBarRef, filtersRef) = createRefs()

        Box(
            Modifier
                .fillMaxWidth()
                .height(WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding() + 5.dp)
                .background(MaterialTheme.colorScheme.outline)
                .constrainAs(statusBarBox) {
                    top.linkTo(parent.top)
                }
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.constrainAs(backBtnRef) {
                start.linkTo(parent.start, 15.dp)
                top.linkTo(statusBarBox.bottom, 10.dp)
            },
        ) {
            Icon(
                Icons.Default.ChevronLeft,
                contentDescription = "Back",
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Complaints",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = FontFamily(Font(Res.font.opensansbold)),
            modifier = Modifier.constrainAs(headingRef) {
                start.linkTo(backBtnRef.end, 5.dp)
                centerVerticallyTo(backBtnRef)
            }
        )

        SearchBar(
            placeholderText = "Search complaint",
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            modifier = Modifier.constrainAs(searchBarRef) {
                top.linkTo(backBtnRef.bottom, 10.dp)
                start.linkTo(parent.start, 5.dp)
                end.linkTo(parent.end, 5.dp)
                width = Dimension.fillToConstraints
            }
        )

        LazyRow(
            modifier = Modifier
                .constrainAs(filtersRef) {
                    top.linkTo(searchBarRef.bottom, margin = 12.dp)
                    centerHorizontallyTo(searchBarRef)
                },
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ComplaintFilter.entries) { status ->
                FilterChip(
                    selected = selectedFilter == status,
                    onClick = { selectedFilter = status },
                    label = {
                        Text(
                            text = status.label,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(Res.font.opensanssemiBold))
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White,
                        containerColor = MaterialTheme.colorScheme.surface,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selectedFilter == status,
                        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        selectedBorderColor = Color.Transparent,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 0.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(36.dp)
                )
            }
        }

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(filtersRef.bottom, margin = 14.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            items(
                items = filteredComplaints,
                key = { it.id }
            ) { complaint ->
                ComplaintCard(
                    complaint = complaint,
                    onLikeClick = {
                        fetchComplaintsVM.voteComplaint(
                            complaintId = complaint.id,
                            voteType = "like"
                        )
                    },
                    onDislikeClick = {
                        fetchComplaintsVM.voteComplaint(
                            complaintId = complaint.id,
                            voteType = "dislike"
                        )
                    },
                    isVotingInProgress = complaint.id in fetchComplaintState.votingInProgress
                )
            }

            if (fetchComplaintState.isLoadingMore || fetchComplaintState.error != null) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        when {
                            fetchComplaintState.isLoadingMore -> {
                                CircularProgressIndicator(
                                    strokeCap = StrokeCap.Round,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            fetchComplaintState.error != null -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = fetchComplaintState.error ?: "Unable to load more",
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(Res.font.opensansregular)),
                                        color = MaterialTheme.colorScheme.error,
                                    )
                                    TextButton(
                                        onClick = {
                                            fetchComplaintsVM.clearError()
                                            fetchComplaintsVM.loadNextItems()
                                        }
                                    ) {
                                        Text("Retry")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (filteredComplaints.isEmpty() && !fetchComplaintState.isLoadingMore && !fetchComplaintState.isRefreshing) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(32.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                Icons.Default.Announcement,
                                contentDescription = "No complaints",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                            Text(
                                text = if (searchQuery.isEmpty()) "No complaints yet" else "No matching complaints",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(Res.font.opensanssemiBold)),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (searchQuery.isNotEmpty()) {
                                Text(
                                    text = "Try adjusting your search",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(Res.font.opensansregular)),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onRegisterCompClick,
            containerColor = MaterialTheme.colorScheme.primary,
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