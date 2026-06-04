package com.messon.student.app.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.messon.student.app.theme.MessonTheme


enum class ComplaintStatus(
    val label: String
) {
    ALL("All"),
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved")
}
enum class UserInteraction {
    LIKE,
    DISLIKE
}

@Composable
fun ComplaintStatus.backgroundColor(): Color {
    return when (this) {
        ComplaintStatus.ALL ->
            MaterialTheme.colorScheme.surface

        ComplaintStatus.PENDING ->
            MessonTheme.colors.warningContainer

        ComplaintStatus.IN_PROGRESS ->
            MessonTheme.colors.infoContainer

        ComplaintStatus.RESOLVED ->
            MessonTheme.colors.accentContainer
    }
}

@Composable
fun ComplaintStatus.textColor(): Color {
    return when (this) {
        ComplaintStatus.ALL ->
            MaterialTheme.colorScheme.onSurface

        ComplaintStatus.PENDING ->
            MaterialTheme.colorScheme.secondary

        ComplaintStatus.IN_PROGRESS ->
            MaterialTheme.colorScheme.primary

        ComplaintStatus.RESOLVED ->
            MaterialTheme.colorScheme.onBackground
    }
}