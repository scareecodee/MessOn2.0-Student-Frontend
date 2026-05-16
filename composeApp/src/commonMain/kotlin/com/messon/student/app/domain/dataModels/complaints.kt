package com.messon.student.app.domain.dataModels

import androidx.compose.ui.graphics.vector.ImageVector
import com.messon.student.app.utils.ComplaintStatus
import com.messon.student.app.utils.UserInteraction

data class ComplaintCategory(val id: String, val label: String, val icon: ImageVector)
data class ComplaintHistory(val id: Int, val type: String, val date: String, val status: String, val text: String)

data class Complaint(
    val id: String,
    val studentName: String,
    val studentId: String,
    val title: String,
    val description: String,
    val status: ComplaintStatus,
    val date: String,
    val likes: Int,
    val dislikes: Int,
    val userInteraction: UserInteraction? = null
)