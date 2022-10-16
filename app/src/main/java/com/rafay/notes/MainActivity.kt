package com.rafay.notes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import com.rafay.notes.databinding.ActivityMainBinding
import com.rafay.notes.home.HomeScreen
import com.rafay.notes.home.HomeViewModelFactory
import com.rafay.notes.theme.NotesTheme
import com.rafay.notes.util.DefaultCoroutineDispatchers
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<ActivityViewModel>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        viewModel.navigation.observe(this) {

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController.popBackStack()
    }
}

class MainComposeActivity : AppCompatActivity() {
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

@Composable
fun NotesActivityContent() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel(
                    factory = HomeViewModelFactory(
                        context = context, dispatchers = DefaultCoroutineDispatchers()
                    )
                ),
                onNoteClicked = {},
            )
        }
    }
}
