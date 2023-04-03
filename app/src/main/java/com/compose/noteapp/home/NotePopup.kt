package com.compose.noteapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.compose.noteapp.R

@Composable
fun NotePopup(
    text: String = "",
    onClickSave: (String) -> Unit,
    onClickDismiss: () -> Unit,
) {

    val textState = rememberSaveable { mutableStateOf(text) }
    Dialog(onDismissRequest = onClickDismiss) {

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colors.background)
        ) {
            BasicTextField(
                modifier = Modifier.padding(
                    start = 16.dp, end = 16.dp,
                    top = 20.dp
                ),
                value = textState.value, onValueChange = { text ->
                    textState.value = text
                })

            Row(
                modifier = Modifier.padding(
                    start = 16.dp, end = 16.dp, bottom = 16.dp
                )
            ) {
                Button(onClick = onClickDismiss) {
                    Text(text = stringResource(id = R.string.dismiss))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onClickSave(textState.value) }) {
                    Text(text = stringResource(id = R.string.save))
                }
            }
        }
    }
}