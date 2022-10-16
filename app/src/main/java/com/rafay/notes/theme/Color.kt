package com.rafay.notes.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class NotesColors(
    primary: Color = Color(0XFF5564ff),
    onPrimary: Color = Color.White,
    secondary: Color = Color(0xFF5564ff),
    onSecondary: Color = Color.White,
){
    var primary by mutableStateOf(primary)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
}