package com.messon.student.app.presentation.mapper
import com.messon.student.app.data.remote.dto.CollegeDto
import com.messon.student.app.domain.dataModels.College

fun CollegeDto.toDomain(): College {
    return College(
        collegeId = collegeId,
        collegeName =collegeName
    )
}