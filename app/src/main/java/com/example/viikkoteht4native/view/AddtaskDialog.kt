package com.example.viikkoteht4native.view

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkoteht4native.model.Task
import com.example.viikkoteht4native.viewmodel.TaskViewModel

@Composable
fun addScreen(taskiViewModeli: TaskViewModel = viewModel(),
              onDismiss: () -> Unit, onAdd: (Task) -> Unit)
{
    var Description by remember {mutableStateOf("")}
    var Title by remember {mutableStateOf("")}
    var DueDate by remember {mutableStateOf("")}

    var task by remember {
        mutableStateOf(Task(id = 0, title = "", description = "", priority = 0, dueDate = "", done = false))
    }

    AlertDialog(
        title = {
            Text("Muokkaa taskia")
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
                onAdd(task)
            })
            {
                Text("Save")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }

        }
    )
}