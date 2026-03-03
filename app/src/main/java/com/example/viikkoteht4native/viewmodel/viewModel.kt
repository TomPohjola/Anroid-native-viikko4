package com.example.viikkoteht4native.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viikkoteht4native.data.TaskRepository
//import com.example.viikkoteht4native.model.Task
import com.example.viikkoteht4native.data.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.collections.listOf


class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _taskilistaState = MutableStateFlow(listOf<Task>())
    var taskilistaState: StateFlow<List<Task>> = repository.allTasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _valittutaski = MutableStateFlow<Task?>(null)
    val valittutaski: StateFlow<Task?> = _valittutaski.asStateFlow()

    private val _taskilistaStateKaikki = MutableStateFlow(listOf<Task>())
    var toggledone: Boolean = false


    /*fun showDone()
    {
        if(!toggledone)
        {
            _taskilistaStateKaikki.value = _taskilistaState.value
            _taskilistaState.value.forEach { task ->
                if(task.done)
                {
                    _taskilistaState.value -= task
                }
            }
            toggledone = true
        }
        else
        {
            _taskilistaState.value = _taskilistaStateKaikki.value
            toggledone = false
        }
    }*/
    fun addTask(uusitaski: Task)
    {
        viewModelScope.launch {
        repository.insert(uusitaski)
        }

    }
    fun removeTask(taskId: Int)
    {
        taskilistaState.value.forEach { task ->
            if(taskId == task.id )
            {
                viewModelScope.launch {
                    repository.delete(task)
            }
        }
        }
        _valittutaski.value = null
    }
    fun toggleDone(itemId: Int)
    {
        taskilistaState.value.mapIndexed { index, item ->
            if (itemId == item.id) {
                viewModelScope.launch {
                    val updated: Boolean = !item.done
                    Log.d("updated", "$updated, $index, $item")
                    repository.toggleTaskStatus(itemId, updated)
                }
            } else {
                item
            }
        }
    }
    fun sortByDate()
    {

    }
    fun updateTask(updated: Task)
    {
        viewModelScope.launch {
            repository.upTask(updated.id, updated.description, updated.title)
        }
        _valittutaski.value = null
    }
    fun closeDialog()
    {
        _valittutaski.value = null
    }
    fun selectTask(taskFromUi: Task)
    {
        _valittutaski.value = taskFromUi
    }
    fun openTask(id: Int) {
        val task = taskilistaState.value.find { it.id == id }
        _valittutaski.value = task
    }

}