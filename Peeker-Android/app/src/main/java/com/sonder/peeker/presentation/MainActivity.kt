package com.sonder.peeker.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sonder.peeker.presentation.document_list.document_screen.DocumentScreen
import com.sonder.peeker.presentation.document_update.DocumentUpdateScreen
import com.sonder.peeker.presentation.notification_list.NotificationListScreen
import com.sonder.peeker.presentation.tag_list.TagListScreen
import com.sonder.peeker.presentation.tag_list.create.TagCreateScreen
import com.sonder.peeker.presentation.notification_list.NotificationSettingsScreen
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
                            route = Screen.RegistrationScreen.route
                        ) {
                            RegistrationScreen(navController)
                        }
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = Screen.DocumentScreen.route + "/{documentId}"
                        ) {
                            DocumentScreen(navController)

                        }
                        composable(
                            route = Screen.UpdateDocumentScreen.route + "/{documentId}"
                        ) {
                            DocumentUpdateScreen(navController)
                        }
                        composable(
                            route = Screen.NotificationsScreen.route
                        ) {
                            NotificationListScreen(navController)
                        }
                        composable(
                            route = Screen.TagsScreen.route
                        ) {
                            TagListScreen(navController)
                        }
                        composable(
                            route = Screen.TagCreateScreen.route
                        ) {
                            TagCreateScreen(navController)
                        }
                        composable(
                            route = Screen.NotificationSettings.route
                        ) {
                            NotificationSettingsScreen(navController)
                        }
                    }
                }
            }
        }
    }
}