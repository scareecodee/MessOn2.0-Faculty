package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.BillReqDto
import com.messon.faculty.app.data.remote.dto.StudentBillDto
import com.messon.faculty.app.domain.dataModel.BillReq
import com.messon.faculty.app.domain.dataModel.StudentBillingData

fun StudentBillDto.toDomain(): StudentBillingData{
    return StudentBillingData(
        name = name,
        rollNo = rollNo,
        branch = branch,
        batch = batch,
        totalBillingPeriods = billingPeriod,
        startDate = startDate,
        endDate = endDate,
        totalBill = billAmount
    )
}


fun BillReq.toDto(): BillReqDto{
    return BillReqDto(
        rangeType=rangeType,
        numOfDays =numOfDays,
        startDate =startDate,
        endDate =endDate
    )
}