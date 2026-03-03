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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.viikkoteht4native.model.Task
import com.example.viikkoteht4native.data.Task
import com.example.viikkoteht4native.viewmodel.TaskViewModel

@Composable
fun addScreen(onDismiss: () -> Unit, onAdd: (Task) -> Unit)
{
    var Description by remember {mutableStateOf("")}
    var Title by remember {mutableStateOf("")}
    var DueDate by remember {mutableStateOf("")}

    var task by remember {
        mutableStateOf(Task(id = 0, title = "", description = "", priority = 0, dueDate = "", done = false))
    }

    val showAlert = remember { mutableStateOf(false) }
    val showAlertText = remember { mutableStateOf(false) }

    if (showAlert.value) {
        AlertScreen(
            showDialog = showAlert.value,
            onDismiss = {showAlert.value = false})
    }
    if (showAlertText.value) {
        AlertScreenEmptyTxtField(
            showDialog = showAlertText.value,
            onDismiss = {showAlertText.value = false})
    }

    AlertDialog(
        title = {
            Text("lisää tehtävä")
        },
        text = {
            Column{
                TextField(
                    modifier = Modifier.width(200.dp),
                    value = Title,
                    onValueChange = { Title = it },
                    label = { Text("anna taskin nimi:") })
                TextField(
                    modifier = Modifier.width(200.dp),
                    value = Description,
                    onValueChange = { Description = it },
                    label = { Text("anna taskin kuvaus:") })
                TextField(
                    modifier = Modifier.width(200.dp),
                    value = DueDate,
                    onValueChange = { DueDate = it },
                    label = { Text("anna taskin takaraja:") })
            }
        },

        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                task = Task(id = 0, title = Title, description = Description, priority = 0, dueDate = DueDate, done = false)
                if(!isValidText(task.dueDate))
                {
                    showAlert.value = true
                }
                else if(Title.trim().isEmpty() || Description.trim().isEmpty())
                {
                    showAlertText.value = true
                }
                else
                {
                    onAdd(task)
                }
            })
            {
                Text("lisää")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }

        }
    )
}
fun isValidText(text: String): Boolean {
    return text.matches(Regex("^(?:[01]?[0-9]|2[0-3]).[0-5]?[0-9](?:.[0-5]?[0-9]?[0-9]?[0-9])?\$"))
}
