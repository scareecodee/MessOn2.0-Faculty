package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.ManagerCreateReqDto
import com.messon.faculty.app.data.remote.dto.ManagerCreateResDto
import com.messon.faculty.app.domain.dataModel.ManagerCreateReq
import com.messon.faculty.app.domain.dataModel.ManagerCreateRes


fun ManagerCreateResDto.toDomain(): ManagerCreateRes{
    return ManagerCreateRes(
//        id = id,
        name = name,
        isActive =isActive,
        mailId = mailId
    )
}

fun ManagerCreateReq.toDto(): ManagerCreateReqDto{
    return ManagerCreateReqDto(
        name = name,
        isActive = isActive,
        mailId = mailId,
    )
}