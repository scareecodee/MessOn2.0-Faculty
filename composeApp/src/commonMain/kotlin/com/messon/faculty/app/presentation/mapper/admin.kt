package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.AdminCreateReqDto
import com.messon.faculty.app.data.remote.dto.AdminCreateResDto
import com.messon.faculty.app.domain.dataModel.AdminCreateReq
import com.messon.faculty.app.domain.dataModel.AdminCreateRes


fun AdminCreateResDto.toDomain(): AdminCreateRes{
    return AdminCreateRes(
        id = id,
        name = name,
        isActive =isActive,
        mailId = mailId
    )
}

fun AdminCreateReq.toDto(): AdminCreateReqDto{
    return AdminCreateReqDto(
        name = name,
        isActive = isActive,
        mailId = mailId,
    )
}