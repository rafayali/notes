package com.rafay.notes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rafay.notes.create.AddNoteModelFactory
import com.rafay.notes.create.AddNoteScreen
import com.rafay.notes.home.HomeScreen
import com.rafay.notes.home.HomeViewModelFactory
import com.rafay.notes.theme.NotesTheme
import com.rafay.notes.util.DefaultCoroutineDispatchers

interface Route {
    val route: String
}

enum class Routes(override val route: String) : Route {
    Home("home"),
    Add("/add")
}

class MainComposeActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            NotesTheme {
                NotesActivityContent()
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun NotesActivityContent() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            HomeScreen(
                viewModel = viewModel(
                    factory = HomeViewModelFactory(
                        context = context, dispatchers = DefaultCoroutineDispatchers()
                    )
                ),
                onNoteClicked = {
                    navController.navigate(Routes.Add.route.plus("/$it"))
                },
                onAddNoteClicked = {
                    navController.navigate(Routes.Add.route)
                }
            )
        }
        composable(
            Routes.Add.route.plus("/{${AddNoteScreen.KEY_LONG_NOTE_ID}}"),
            arguments = listOf(
                navArgument(AddNoteScreen.KEY_LONG_NOTE_ID) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments!!.getLong(AddNoteScreen.KEY_LONG_NOTE_ID)
            AddNoteScreen(
                viewModel = viewModel( factory = AddNoteModelFactory(context, noteId = noteId)),
                onClose = {
                    navController.navigateUp()
                },
            )
        }
        composable(Routes.Add.route){
            AddNoteScreen(
                viewModel = viewModel(factory = AddNoteModelFactory(context = context)),
                onClose = {
                    navController.navigateUp()
                },
            )
        }
    }
}
