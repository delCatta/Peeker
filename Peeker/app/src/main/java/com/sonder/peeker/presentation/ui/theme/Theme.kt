package com.sonder.peeker.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Pink,
    secondary = Pink,
    background = Graphite,
    onBackground = White,
    onPrimary = Graphite,
    onSecondary = Graphite
)

private val LightColorPalette = lightColors(
    primary = Pink,
    secondary = Pink,
    background = Color.White,
    onBackground = Gray,
    onPrimary = Graphite,
    onSecondary = Graphite

)

@Composable
fun PeekerTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}