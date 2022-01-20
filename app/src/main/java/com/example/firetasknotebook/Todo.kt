package com.example.firetasknotebook

import com.google.firebase.firestore.DocumentId
import java.sql.Timestamp

data class Todo(
    @DocumentId
    var id: String = "",
    val content: String = "",
    val isAchieved: Boolean = false,
    val created_at: Timestamp? = null,
    val achieved_at: Timestamp? = null,
)