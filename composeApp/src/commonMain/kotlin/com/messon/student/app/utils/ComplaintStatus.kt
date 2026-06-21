package com.messon.student.app.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.messon.student.app.domain.dataModels.ComplaintStatus
import com.messon.student.app.theme.MessonTheme


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class ComplaintFilter(
    val label: String
) {
    ALL("All"),
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved")
}

@Composable
fun ComplaintFilter.backgroundColor(): Color {
    return when (this) {
        ComplaintFilter.ALL ->
            MaterialTheme.colorScheme.surface

        ComplaintFilter.PENDING ->
            MessonTheme.colors.warningContainer

        ComplaintFilter.IN_PROGRESS ->
            MessonTheme.colors.infoContainer

        ComplaintFilter.RESOLVED ->
            MessonTheme.colors.accentContainer
    }
}

@Composable
fun ComplaintFilter.textColor(): Color {
    return when (this) {
        ComplaintFilter.ALL ->
            MaterialTheme.colorScheme.onSurface

        ComplaintFilter.PENDING ->
            MaterialTheme.colorScheme.secondary

        ComplaintFilter.IN_PROGRESS ->
            MaterialTheme.colorScheme.primary

        ComplaintFilter.RESOLVED ->
            MaterialTheme.colorScheme.onBackground
    }
}


fun ComplaintStatus.toFilter(): ComplaintFilter {
    return when (this) {
        ComplaintStatus.PENDING -> ComplaintFilter.PENDING
        ComplaintStatus.IN_PROGRESS -> ComplaintFilter.IN_PROGRESS
        ComplaintStatus.RESOLVED -> ComplaintFilter.RESOLVED
    }
}


