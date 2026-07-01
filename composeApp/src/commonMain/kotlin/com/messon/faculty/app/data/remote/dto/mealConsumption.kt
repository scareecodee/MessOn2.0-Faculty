package com.messon.faculty.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MealDetailDto(

    @SerialName("meal_type")
    val mealType: String?,

    @SerialName("consumed")
    val hasConsumed: Boolean,

    @SerialName("selected")
    val hasOpted: Boolean,
)


@Serializable
data class StudentMealDayDto(

    @SerialName("date")
    val date: String,

    @SerialName("lunch")
    val lunch: MealDetailDto,

    @SerialName("breakfast")
    val breakfast: MealDetailDto,

    @SerialName("dinner")
    val dinner: MealDetailDto
)
