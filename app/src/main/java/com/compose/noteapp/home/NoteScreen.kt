package com.compose.noteapp.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.compose.model.NoteEntity
import com.compose.noteapp.R
import com.compose.viewmodel.HomeViewModelAbstract


@Composable
fun NoteScreen(
    viewModel: HomeViewModelAbstract,
    onClickClose: () -> Unit
) {
    val note = viewModel.selectedNoteState.value
    val textState = rememberSaveable {
        mutableStateOf(
            note?.text
                ?: ""
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit") },
                actions = {
                    Button(onClick = {
                        note?.let {
                         viewModel.addOrUpdateNote(it.copy(text = textState.value))
                        }?: run{
                            viewModel.addOrUpdateNote(NoteEntity(text = textState.value))
                        }
                        onClickClose()
                    }) {
                        Text(text = stringResource(id = R.string.save))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onClickClose() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack
                            , contentDescription = "Arrow Back" )
                    }
                }
            )
        }

    ) {


        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)

        ) {


            BasicTextField(
                modifier = Modifier
                    .padding(
                        start = 16.dp, end = 16.dp,
                        top = 20.dp
                    )
                    .fillMaxWidth()
                    .fillMaxHeight(),
                value = textState.value, onValueChange = { text ->
                    textState.value = text
                })


        }

        Text(
            modifier = Modifier.padding(it),
            text = ""
        )
    }
}