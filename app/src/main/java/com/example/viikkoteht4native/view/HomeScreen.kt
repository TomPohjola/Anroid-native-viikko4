package com.example.viikkoteht4native.view

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viikkoteht4native.viewmodel.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
//import com.example.viikkoteht4native.model.Task
import com.example.viikkoteht4native.data.Task


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homescreen(taskiViewModeli: TaskViewModel = viewModel(),
               onNavigateCalendar: () -> Unit = {},
               modifier: Modifier = Modifier,
               )
{
    val taskilistaState by taskiViewModeli.taskilistaState.collectAsState()
    val valittutaski by taskiViewModeli.valittutaski.collectAsState()

    var showAdd = remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    if (valittutaski != null) {
        DetailScreen(valittutask = valittutaski!!,
            onDismiss = { taskiViewModeli.closeDialog() },
            onUpdate = { taskiViewModeli.updateTask(it)},
            onDelete = { taskiViewModeli.removeTask(it.id)
                             })
    }
    if (showAdd.value) {
        addScreen(
            onDismiss = { showAdd.value = false },
            onAdd = { taskiViewModeli.addTask(it)
                       showAdd.value = false }
        )
    }

    Column(modifier
        .padding(15.dp)
        .verticalScroll(rememberScrollState())
        .width(screenWidth)
        .height(screenHeight),
        horizontalAlignment = Alignment.CenterHorizontally) {

        TopAppBar(
            title = { Text("Task List") },
            actions = {
                IconButton(onClick = onNavigateCalendar) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Go to calendar"
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .width(screenWidth)
                .height(screenHeight / 2)
                .border(2.dp, Color.Black),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            itemsIndexed(taskilistaState) { index, task ->
                Text(
                    text = "${task.title}",
                    fontSize = 25.sp
                )
                Text(
                    text = "${task.description}",
                    fontSize = 20.sp)

                if (!taskiViewModeli.toggledone) {
                    Row(modifier = Modifier.height(40.dp))
                    {
                        Checkbox(
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.DarkGray,
                                uncheckedColor = Color.DarkGray,
                                checkmarkColor = Color.White),

                            checked = taskiViewModeli.taskilistaState.collectAsState().value[index].done,
                            onCheckedChange = {
                                Log.d("id", "${task.id}")
                                taskiViewModeli.toggleDone(task.id)
                            })
                        Spacer(Modifier.weight(1f))
                        ElevatedButton(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            border = BorderStroke(1.dp, Color.Black),
                            onClick = {
                                taskiViewModeli.selectTask(task)
                            },
                        )
                        {
                            Text(text = "Muokkaa")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                HorizontalDivider(
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .padding(10.dp)
                .height(screenHeight / 3),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(Modifier.padding(10.dp))
            ElevatedButton(
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                border = BorderStroke(1.dp, Color.Black),
                onClick = {showAdd.value = true}
            )
            {
                Text(text = "lisää tehtävä")
            }
        }
    }
}

