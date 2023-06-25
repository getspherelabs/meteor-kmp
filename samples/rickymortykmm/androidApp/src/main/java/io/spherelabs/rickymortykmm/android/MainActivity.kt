package io.spherelabs.rickymortykmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.spherelabs.rickymortykmm.android.ui.CharactersRoute
import io.spherelabs.rickymortykmm.android.ui.detail.CharacterDetailRoute
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "characters") {
                        composable("characters") {
                            CharactersRoute(viewModel = koinViewModel(), navigateToDetail = { newId ->
                                navController.navigate("detail/$newId")
                            })
                        }
                        composable("detail/{id}") { entry ->
                            val newId = entry.arguments?.getString("id")?.toInt() ?: 0

                            CharacterDetailRoute(id = newId, viewModel = koinViewModel())
                        }
                    }
                }
            }
        }
    }
}
