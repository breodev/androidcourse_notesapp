package com.example.notesapp.model

data class Note(
    val id: Int,         // Unique identifier for the note
    val title: String,   // Title of the note
    val content: String  // Main content or body of the note
)