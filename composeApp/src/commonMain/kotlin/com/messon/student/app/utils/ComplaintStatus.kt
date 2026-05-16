package com.messon.student.app.utils

import androidx.compose.ui.graphics.Color
import com.messon.student.app.theme.CustomColors.BlackberryCream
import com.messon.student.app.theme.CustomColors.Cream
import com.messon.student.app.theme.CustomColors.HoneyBronze
import com.messon.student.app.theme.CustomColors.LightBlue
import com.messon.student.app.theme.CustomColors.LightLavender
import com.messon.student.app.theme.CustomColors.RichCerulean

enum class ComplaintStatus(val label: String, val bgColor: Color, val textColor: Color) {
    ALL("All", Color.White, BlackberryCream),
    PENDING("Pending", Cream, HoneyBronze),
    IN_PROGRESS("In Progress", LightBlue, RichCerulean),
    RESOLVED("Resolved", LightLavender, BlackberryCream)
}

enum class UserInteraction {
    LIKE,
    DISLIKE
}