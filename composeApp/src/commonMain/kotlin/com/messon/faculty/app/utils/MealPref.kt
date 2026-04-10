package com.messon.faculty.app.utils

enum class MealType {
    VEG, NON_VEG, NONE;

    companion object {
        fun from(value: String?): MealType {
            return when (value) {
                "veg" -> VEG
                "non_veg" -> NON_VEG
                else -> NONE
            }
        }
    }
}