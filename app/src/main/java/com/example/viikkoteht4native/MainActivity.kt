package com.example.viikkoteht4native

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.viikkoteht4native.navigation.ROUTE_HOME
import com.example.viikkoteht4native.navigation.ROUTE_CALENDAR
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.viikkoteht4native.view.CalendarScreen
import com.example.viikkoteht4native.view.Homescreen
import com.example.viikkoteht4native.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: TaskViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = ROUTE_HOME
            ) {

                composable(ROUTE_HOME) {
                            Homescreen(
                                taskiViewModeli = viewModel,
                                onNavigateCalendar = {
                                    navController.navigate(ROUTE_CALENDAR)
                                },
                                onAddClick = {
                                    viewModel.addTaskDialogVisible.value = true
                                },)

                        }



                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(
                            taskiViewModeli = viewModel,
                            onTaskClick = { id ->
                                viewModel.openTask(id)
                            },
                            onNavigateHome = {
                                navController.navigate(ROUTE_HOME)
                            }
                        )
                    }
                }
            }

        }
    }
