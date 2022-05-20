package com.sonder.peeker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.sonder.peeker.data.remote.PeekerApi
import com.sonder.peeker.domain.repository.DocumentRepositoryImpl
import com.sonder.peeker.presentation.document_create.DocumentCreateScreen
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
                    HomeScreen()
                }
            }
        }
    }
}