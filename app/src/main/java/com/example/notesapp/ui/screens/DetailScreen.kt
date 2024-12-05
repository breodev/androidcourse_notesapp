package com.example.notesapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notesapp.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, noteViewModel: NoteViewModel, noteId: Int) {
    // Retrieve the note by its ID
    val note = noteViewModel.getNoteById(noteId)
    // State variables to hold the editable title and content
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }
    val context = LocalContext.current // Context for displaying Toast messages

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Note Details") }) // App bar with the title "Note Details"
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize() // Fill the available space
                .padding(16.dp), // Add padding around the content
            verticalArrangement = Arrangement.Center // Center the content vertically
        ) {
            // Editable text field for the note title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it }, // Update the title state on input
                label = { Text("Title") }, // Label for the text field
                modifier = Modifier.fillMaxWidth() // Field takes up the full width
            )
            Spacer(modifier = Modifier.height(16.dp)) // Space between fields

            // Editable text field for the note content
            OutlinedTextField(
                value = content,
                onValueChange = { content = it }, // Update the content state on input
                label = { Text("Content") }, // Label for the text field
                modifier = Modifier
                    .fillMaxWidth() // Field takes up the full width
                    .height(150.dp) // Set a fixed height for the content field
            )
            Spacer(modifier = Modifier.height(24.dp)) // Space before buttons

            // Row for arranging Update and Delete buttons horizontally
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Evenly space the buttons
            ) {
                // Update button
                Button(onClick = {
                    if (title.isNotBlank()) { // Ensure the title is not blank
                        noteViewModel.updateNoteById(noteId, title.trim(), content.trim())
                        Toast.makeText(context, "Note Updated Successfully!", Toast.LENGTH_SHORT).show() // Show success message
                        navController.popBackStack() // Navigate back to the previous screen
                    } else {
                        Toast.makeText(context, "Title cannot be empty!", Toast.LENGTH_SHORT).show() // Show error message
                    }
                }) {
                    Text("Update") // Button label
                }
                // Delete button
                Button(onClick = {
                    noteViewModel.deleteNoteById(noteId) // Delete the note by ID
                    Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show() // Show deletion message
                    navController.popBackStack() // Navigate back to the previous screen
                }) {
                    Text("Delete") // Button label
                }
            }
        }
    }
}