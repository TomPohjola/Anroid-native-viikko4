package com.example.viikkoteht4native.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkoteht4native.viewmodel.TaskViewModel
import com.example.viikkoteht4native.model.Task
import com.example.viikkoteht4native.view.DetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CalendarScreen(
    taskiViewModeli: TaskViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onTaskClick: (Int) -> Unit = {},
    onNavigateHome: () -> Unit
) {
    val taskilistaState by taskiViewModeli.taskilistaState.collectAsState()
    val valittutaski by taskiViewModeli.valittutaski.collectAsState()

    val grouped = taskilistaState.groupBy { it.dueDate ?: "No date" }

    if (valittutaski != null) {
        DetailScreen(valittutask = valittutaski!!,
            onDismiss = { taskiViewModeli.closeDialog() },
            onUpdate = { taskiViewModeli.updateTask(it)},
            onDelete = { taskiViewModeli.removeTask(it.id)})
    }

    Column(modifier = Modifier.padding(16.dp)) {

        TopAppBar(
            title = { Text("Calendar") },
            navigationIcon = {
                IconButton(onClick = onNavigateHome) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Go to list")
                }
            }
        )
// --- 3. Kalenterinäkymä (päiväryhmitys) ---
        LazyColumn {
            grouped.forEach { (date, tasksOfDay) ->
                // Päivämäärä otsikkona
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }

                // Tehtävät kyseiselle päivälle
                items(tasksOfDay) { task ->
                    CalendarTaskCard(
                        task = task,
                        // ei enää viewModel.openTask, vaan callback
                        onTaskClick = { id -> onTaskClick(id) }
                        // tai lyhyesti: onTaskClick = onTaskClick
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarTaskCard(
    task: Task,
    onTaskClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onTaskClick(task.id) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleMedium)
            if (task.description.isNotBlank()) {
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}