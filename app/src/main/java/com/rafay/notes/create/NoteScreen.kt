package com.rafay.notes.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.color.MaterialColors
import com.rafay.notes.R

@Composable
fun NoteScreen() {
    val viewModel = viewModel<CreateNoteViewModel>()

    val title by viewModel.title.collectAsState()
    val notes by viewModel.notes.collectAsState()

    NoteScreenContent(
        title = title ?: "",
        notes = notes ?: "",
        onTitleChange = viewModel::setTitle,
        onNotesChange = viewModel::setNotes,
        onClose = {},
    )
}

@Composable
fun NoteScreenContent(
    title: String,
    notes: String,
    onTitleChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            IconButton(onClick = onClose) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_black_24dp),
                    contentDescription = null,
                )
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().padding(0.dp),
                value = title,
                onValueChange = onTitleChange,
                placeholder = { Text(text = stringResource(R.string.note__palceholder_title)) },
                singleLine = true,
                shape = RectangleShape,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = notes,
                onValueChange = onNotesChange,
                placeholder = { Text(text = stringResource(R.string.note__placeholder_description)) },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface)
            )
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
@Preview
fun NoteScreenContent_Preview() {
    MaterialTheme {
        NoteScreenContent(
            title = "Hello", notes = "This is a random note", onTitleChange = {},
            onNotesChange = {}, onClose = {},
        )
    }
}