package com.example.notesapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.ui.screens.AddNoteScreen
import com.example.notesapp.ui.screens.DetailScreen
import com.example.notesapp.ui.screens.MainScreen
import com.example.notesapp.viewmodel.NoteViewModel

@Composable
fun NotesApp() {
    val navController = rememberNavController() // Create a navigation controller for handling navigation
    val noteViewModel: NoteViewModel = viewModel() // Obtain an instance of the NoteViewModel

    // Define the navigation host for the app
    NavHost(
        navController = navController, // The navigation controller used to manage navigation
        startDestination = "main" // Set the starting screen of the app
    ) {
        // Define the composable for the main screen
        composable("main") {
            MainScreen(navController, noteViewModel) // Pass the navController and ViewModel to the MainScreen
        }
        // Define the composable for the add note screen
        composable("add_note") {
            AddNoteScreen(navController, noteViewModel) // Pass the navController and ViewModel to AddNoteScreen
        }
        // Define the composable for the detail screen with a dynamic note ID
        composable("detail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() // Extract the note ID from the route arguments
            if (noteId != null) {
                DetailScreen(navController, noteViewModel, noteId) // Pass the note ID to DetailScreen if it is valid
            }
        }
    }
}