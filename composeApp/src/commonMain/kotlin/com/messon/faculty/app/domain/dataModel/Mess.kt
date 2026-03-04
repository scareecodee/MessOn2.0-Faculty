package com.messon.faculty.app.domain.dataModel

data class Manager(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)

data class Caterer(
    val id: Int,
    val name: String,
    val managers: List<Manager>
)