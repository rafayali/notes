package com.rafay.notes.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun NotesTheme(isDark: Boolean = false, content: @Composable () -> Unit) {
    val localColors = remember { NotesColors() }

    val localTypography = remember { NotesTypography() }

    val lightMaterialColors = lightColors(
        primary = localColors.primary,
        secondary = localColors.secondary,
        onPrimary = localColors.onPrimary,
        onSecondary = localColors.onSecondary
    )

    val materialTypography = Typography()

    ProvidesNotesTheme(colors = localColors, typography = localTypography) {
        MaterialTheme(
            colors = lightMaterialColors,
            typography = materialTypography,
            content = content
        )
    }
}

@Composable
fun ProvidesNotesTheme(
    colors: NotesColors, typography: NotesTypography, content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalNotesColors provides colors,
        LocalNotesTypography provides typography,
        content = content,
    )
}

private val LocalNotesColors = staticCompositionLocalOf<NotesColors> {
    error("No USDColors provided")
}

private val LocalNotesTypography = staticCompositionLocalOf<NotesTypography> {
    error("No USDTypography provided")
}