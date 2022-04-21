package com.sonder.peeker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.sonder.peeker.presentation.ui.theme.PeekerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeekerTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    
                }
            }
        }
    }
}