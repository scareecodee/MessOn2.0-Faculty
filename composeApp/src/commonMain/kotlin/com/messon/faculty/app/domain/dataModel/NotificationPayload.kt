package com.messon.faculty.app.domain.dataModel

import kotlinx.serialization.Serializable

@Serializable
data class NotificationPayload(
    val messageId: String,
    val type: String,
    val title: String,
    val body: String,
    val deepLink: String? = null,
    val imageUrl: String? = null,
    val metadata: Map<String, String> = emptyMap(),
    val sentAt: Long = 0L
)