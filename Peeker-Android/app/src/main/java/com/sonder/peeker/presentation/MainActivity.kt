package com.sonder.peeker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.presentation.document_create.DocumentCreateScreen
import com.sonder.peeker.presentation.ui.screens.LoginScreen
import com.sonder.peeker.presentation.ui.screens.RegistrationScreen
import com.sonder.peeker.presentation.ui.theme.HomeScreen
import com.sonder.peeker.presentation.ui.theme.PeekerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeekerTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeScreen.route
                    ) {
                        composable(
                            route = Screen.LoginScreen.route
                        ) {
                            LoginScreen(navController)
                        }
                        composable(
                            route = Screen.RegistrationScreen.route //+ "/{coinId}"
                        ) {
                            RegistrationScreen(navController)
                        }
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = Screen.NewDocumentScreen.route
                        ) {
                            DocumentCreateScreen(navController)
                        }
                    }
                }
            }
        }
    }
}