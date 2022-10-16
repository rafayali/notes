package com.rafay.notes.common.view

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NotesTopAppBar(){
    val insets = WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues()
}

@Composable
@Preview
fun NotesTopAppBar_Preview(){
}