package com.messon.student.app.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleanHands
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class ComplaintCategory(
    val id: String,
    val label: String,
    val icon: ImageVector
) {
    FOOD("food", "Food Quality", Icons.Default.Restaurant),
    HYGIENE("hygiene", "Hygiene", Icons.Default.CleanHands),
    SERVICE("service", "Staff Behavior", Icons.Default.Person),
    INFRA("infra", "Infrastructure", Icons.Default.Settings)
}