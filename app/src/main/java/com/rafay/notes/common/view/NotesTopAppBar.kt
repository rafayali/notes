package com.rafay.notes.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.rafay.notes.R
import com.rafay.notes.theme.NotesColors
import com.rafay.notes.theme.NotesTheme

@Composable
fun NotesTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onNavigationIconClicked: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val insets = WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues()

    Box(modifier = modifier.background(color = MaterialTheme.colors.primarySurface).padding(insets)){
        TopAppBar(
            title = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
            navigationIcon = onNavigationIconClicked?.let {
                {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            },
            actions = actions,
        )
    }
}

@Composable
@Preview
fun NotesTopAppBar_Preview() {
    NotesTheme {
        NotesTopAppBar(
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            onNavigationIconClicked = { /*TODO*/ },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_24dp),
                        contentDescription = null,
                    )
                }
            }
        )
    }
}