package com.rafay.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.rafay.notes.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<ActivityViewModel>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Invoked")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        viewModel.navigation.observe(this) { loggedIn ->
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            if (!loggedIn) {
                if (navController.currentDestination!!.id != R.id.loginFragment) {
                    navController.navigate(
                        R.id.auth_graph,
                        null,
                        navOptions {
                            popUpTo(navController.currentDestination!!.id) {
                                inclusive = true
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController.popBackStack()
    }
}
