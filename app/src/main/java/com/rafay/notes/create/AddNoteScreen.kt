package com.rafay.notes.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafay.notes.R
import com.rafay.notes.common.view.NotesTopAppBar
import com.rafay.notes.theme.NotesTheme
import com.rafay.notes.util.EMPTY_STRING

object AddNoteScreen{
    const val KEY_LONG_NOTE_ID = "id"
}

@Composable
fun AddNoteScreen(viewModel: AddNoteViewModel, onClose: () -> Unit) {
    val title by viewModel.title.collectAsState()
    val notes by viewModel.notes.collectAsState()

    AddNoteScreenContent(
        title = title ?: EMPTY_STRING,
        notes = notes ?: EMPTY_STRING,
        onTitleChange = viewModel::setTitle,
        onNotesChange = viewModel::setNotes,
        onClose = {
            viewModel.sync()
            onClose()
        },
    )
}

@Composable
private fun AddNoteScreenContent(
    title: String,
    notes: String,
    onTitleChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            NotesTopAppBar(
                navigationIcon = R.drawable.ic_close_black_24dp,
                onNavigationIconClicked = onClose,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding),
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                value = title,
                onValueChange = onTitleChange,
                placeholder = {
                    Text(
                        text = stringResource(R.string.note__palceholder_title),
                        style = MaterialTheme.typography.h6
                    )
                },
                shape = RectangleShape,
                textStyle = MaterialTheme.typography.h6,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {

                    },
                )
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = notes,
                onValueChange = onNotesChange,
                placeholder = { Text(text = stringResource(R.string.note__placeholder_description)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

@Composable
@Preview
fun NoteScreenContent_Preview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    NotesTheme {
        AddNoteScreenContent(
            title = title, notes = description,
            onTitleChange = { title = it },
            onNotesChange = { description = it },
            onClose = {},
        )
    }
}