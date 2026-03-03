package com.example.viikkoteht4native

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkoteht4native.view.Homescreen
import com.example.viikkoteht4native.viewmodel.TaskViewModel
import com.example.viikkoteht4native.data.AppDatabase
import com.example.viikkoteht4native.data.TaskRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikkoteht4native.navigation.ROUTE_CALENDAR
import com.example.viikkoteht4native.navigation.ROUTE_HOME
import com.example.viikkoteht4native.ui.theme.Viikkoteht4nativeTheme
import com.example.viikkoteht4native.view.CalendarScreen

/*class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }

    private val repository by lazy {
        TaskRepository(database.taskDao())
    }

    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Viikkoteht4nativeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Homescreen(modifier = Modifier.padding(innerPadding), taskiViewModeli = viewModel)
                }
            }
        }
    }
}*/

class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }

    private val repository by lazy {
        TaskRepository(database.taskDao())
    }

    private val viewModel: TaskViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

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
                       )
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