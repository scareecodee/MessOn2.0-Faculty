package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.HolidayDto
import com.messon.faculty.app.data.remote.dto.HolidayResDto
import com.messon.faculty.app.data.remote.dto.MessCreateReqDto
import com.messon.faculty.app.data.remote.dto.MessCreateResDto
import com.messon.faculty.app.domain.dataModel.Holiday
import com.messon.faculty.app.domain.dataModel.HolidayRes
import com.messon.faculty.app.domain.dataModel.MessCreateReq
import com.messon.faculty.app.domain.dataModel.MessCreateRes

fun MessCreateResDto.toDomain(): MessCreateRes{
    return MessCreateRes(
        id = id,
        name = name,
        isActive =isActive,
        location = location
    )
}

fun MessCreateReq.toDto(): MessCreateReqDto{
    return MessCreateReqDto(
        name = name,
        location = location,
        isActive = isActive
    )
}


fun Holiday.toDTo(): HolidayDto{
    return HolidayDto(
        startDate = startDate,
        endDate = endDate,
        reason = reason
    )
}


fun HolidayResDto.toDomain(): HolidayRes{
    return HolidayRes(
        holidayId = holidayId,
        startDate = startDate,
        endDate =endDate,
        reason =reason
    )
}