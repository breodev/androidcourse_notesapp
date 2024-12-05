package com.example.notesapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, noteViewModel: NoteViewModel) {
    // Observing the notes from the ViewModel as state
    val notes by noteViewModel.notes.observeAsState(emptyList())

    // Scaffold provides the basic structure for the screen
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("NotesApp") }) // Top app bar with title
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_note") } // Navigate to the Add Note screen
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note") // Icon for the FAB
            }
        }
    ) { innerPadding ->
        // LazyColumn to display a list of notes
        LazyColumn(
            modifier = Modifier.fillMaxSize(), // Fill the available space
            contentPadding = innerPadding // Add padding around the content
        ) {
            // Iterate through the list of notes and display each as a NoteItem
            items(notes) { note ->
                NoteItem(
                    note = note,
                    onClick = {
                        navController.navigate("detail/${note.id}") // Navigate to the detail screen for the selected note
                    }
                )
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    // Card component for individual note display
    Card(
        modifier = Modifier
            .fillMaxWidth() // Card takes up the full width of the screen
            .padding(vertical = 4.dp) // Vertical spacing between cards
            .clickable(onClick = onClick), // Clickable to trigger navigation or actions
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)// Elevation for shadow effect
    ) {
        // Column to arrange the title and content of the note vertically
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title, // Display the title of the note
                style = MaterialTheme.typography.labelSmall // Apply headline 6 typography
            )
            Spacer(modifier = Modifier.height(4.dp)) // Small space between title and content
            Text(
                text = note.content, // Display the content of the note
                style = MaterialTheme.typography.bodyMedium // Apply body 2 typography
            )
        }
    }
}