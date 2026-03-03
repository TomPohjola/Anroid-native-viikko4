package com.example.viikkoteht4native.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import com.example.viikkoteht4native.model.Task
import com.example.viikkoteht4native.data.Task

@Composable
fun DetailScreen(valittutask: Task, onDismiss: () -> Unit, onUpdate: (Task) -> Unit, onDelete: (Task) -> Unit)
{
    var changeDes by remember {mutableStateOf("")}
    var changeTitle by remember {mutableStateOf("")}

    val showAlertText = remember { mutableStateOf(false) }

    if (showAlertText.value) {
        AlertScreenEmptyTxtField(
            showDialog = showAlertText.value,
            onDismiss = {showAlertText.value = false})
    }

    AlertDialog(
        title = {
            Text("Muokkaa taskia")
        },
        text = {
            Column{
                TextField(
                    modifier = Modifier.width(200.dp),
                    value = changeTitle,
                    onValueChange = { changeTitle = it },
                    label = { Text("muuta taskin nimi:") })
                TextField(
                    modifier = Modifier.width(200.dp),
                    value = changeDes,
                    onValueChange = { changeDes = it },
                    label = { Text("muuta taskin kuvaus:") })
            }
        },

        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if(!changeTitle.trim().isEmpty() || !changeDes.trim().isEmpty())
                {
                    onUpdate(valittutask.copy(title = changeTitle, description = changeDes))
                }
                else
                {
                    showAlertText.value = true
                }
            })
            {
                Text("Save")
            }
            TextButton(onClick = {
                onDelete(valittutask)
            })
            {
                Text("Delete")
            }

        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }

        }
    )
}

