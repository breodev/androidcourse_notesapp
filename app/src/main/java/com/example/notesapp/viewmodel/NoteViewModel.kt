package com.example.notesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.model.Note

class NoteViewModel : ViewModel() {

    // Backing property for notes, using MutableLiveData to allow updates
    private val _notes = MutableLiveData<MutableList<Note>>(mutableListOf())
    // Publicly exposed LiveData for observing the notes list
    val notes: LiveData<MutableList<Note>> = _notes

    // Variable to keep track of the next unique ID for a new note
    private var nextId = 1

    // Function to add a new note to the list
    fun addNote(title: String, content: String) {
        val newNote = Note(
            id = nextId++,   // Assign a unique ID and increment the counter
            title = title,   // Set the title of the new note
            content = content // Set the content of the new note
        )
        _notes.value?.add(newNote) // Add the new note to the existing list
        _notes.value = _notes.value // Trigger observers by reassigning the value
    }

    // Function to update an existing note by its ID
    fun updateNoteById(id: Int, title: String, content: String) {
        _notes.value = _notes.value?.map { note ->
            // Replace the note with a matching ID with an updated copy
            if (note.id == id) note.copy(title = title, content = content) else note
        }?.toMutableList() // Convert the result back to a mutable list
    }

    // Function to delete a note by its ID
    fun deleteNoteById(id: Int) {
        _notes.value = _notes.value?.filter { it.id != id }?.toMutableList() // Remove the note with the matching ID
    }

    // Function to retrieve a note by its ID
    fun getNoteById(id: Int): Note? {
        return _notes.value?.find { it.id == id } // Find the note with the matching ID
    }
}