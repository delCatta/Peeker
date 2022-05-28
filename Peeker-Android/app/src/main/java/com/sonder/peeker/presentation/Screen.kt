package com.sonder.peeker.presentation


sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object RegistrationScreen: Screen("registration_screen")
    object HomeScreen: Screen("home_screen")
    object DocumentScreen: Screen("document_screen")
    object NewDocumentScreen: Screen("new_document_screen")
}