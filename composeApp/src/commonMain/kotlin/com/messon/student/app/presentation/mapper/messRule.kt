package com.messon.student.app.presentation.mapper

import com.messon.student.app.data.remote.dto.MessRuleDto
import com.messon.student.app.domain.dataModels.MessRule


fun MessRule.toDto(): MessRuleDto{
    return MessRuleDto(
        day = day,
        isCompulsory = isCompulsory
    )
}


fun MessRuleDto.toDomain(): MessRule{
    return MessRule(
        day = day,
        isCompulsory = isCompulsory
    )
}