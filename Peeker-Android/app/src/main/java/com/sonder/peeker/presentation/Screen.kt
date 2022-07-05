package com.sonder.peeker.presentation


sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object RegistrationScreen: Screen("registration_screen")
    object HomeScreen: Screen("home_screen")
    object DocumentScreen: Screen("document_screen")
    object UpdateDocumentScreen: Screen("update_document_screen")
    object NotificationsScreen: Screen("notifications_screen")
    object NotificationSettings: Screen("notification_settings")
}