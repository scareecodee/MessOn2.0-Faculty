package com.messon.faculty.app.domain.dataModel

data class MealDetail(
    val meaType: String?,
    val consumed: Boolean,
    val selected: Boolean
)

data class StudentMealDay(
    val date: String,
    val breakfast: MealDetail,
    val lunch: MealDetail,
    val dinner: MealDetail
)