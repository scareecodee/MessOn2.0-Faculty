package com.messon.faculty.app.domain.dataModel

import com.messon.faculty.app.utils.MealType

data class DayPreference(
    val dayName: String,
    val breakfast: MealType,
    val lunch: MealType,
    val dinner: MealType)
