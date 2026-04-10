package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.MealDetailDto
import com.messon.faculty.app.data.remote.dto.StudentDetailedBillHistoryDto
import com.messon.faculty.app.data.remote.dto.StudentMealDayDto
import com.messon.faculty.app.domain.dataModel.MealDetail
import com.messon.faculty.app.domain.dataModel.StudentDetailedBillHistory
import com.messon.faculty.app.domain.dataModel.StudentMealDay

fun StudentMealDayDto.toDomain(): StudentMealDay{
    return StudentMealDay(
        date = date,
        breakfast = breakfast.toDomain(),
        lunch = lunch.toDomain(),
        dinner =dinner.toDomain()
    )
}


fun MealDetailDto.toDomain(): MealDetail{
    return MealDetail(
        meaType = mealType,
        consumed = hasConsumed,
        selected = hasOpted
    )
}


fun StudentDetailedBillHistoryDto.toDomain(): StudentDetailedBillHistory{
    return StudentDetailedBillHistory(
        history = history.map {
            it.toDomain()
        },
        bill = bill.map {
            it.toDomain()
        }
    )
}