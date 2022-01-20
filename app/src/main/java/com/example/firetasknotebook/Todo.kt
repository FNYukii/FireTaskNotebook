package com.example.firetasknotebook

import com.google.firebase.firestore.DocumentId
import java.sql.Timestamp

data class Todo(
    @DocumentId
    var id: String = "",
    val content: String = "",
    val isAchieved: Boolean = false,
    val createdAt: Timestamp? = null,
    val achievedAt: Timestamp? = null,
)