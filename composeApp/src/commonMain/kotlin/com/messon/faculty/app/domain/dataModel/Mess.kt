package com.messon.faculty.app.domain.dataModel

data class Manager(
    val id: Int,
    val name: String,
    val email: String="",
)

data class Mess(
    val id: Int,
    val name: String,
    val managers: List<Manager>,
    val status: Boolean,
    val location: String="D Block G Floor"
)