package com.example.firetasknotebook

import com.google.firebase.firestore.DocumentId

data class Todo(
    @DocumentId
    var id: String = "",
    val content: String = ""
)