package com.example.viikkoteht4native.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.viikkoteht4native.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.listOf


class TaskViewModel : ViewModel() {
    private val _taskilistaState = MutableStateFlow(listOf<Task>())
    val taskilistaState: StateFlow<List<Task>> = _taskilistaState.asStateFlow()

    private val _valittutaski = MutableStateFlow<Task?>(null)
    val valittutaski: StateFlow<Task?> = _valittutaski.asStateFlow()

    private val _taskilistaStateKaikki = MutableStateFlow(listOf<Task>())
    var toggledone: Boolean = false

    val addTaskDialogVisible = MutableStateFlow<Boolean>(false)

    init
    {
        _taskilistaState.value = listOf(
            Task(1, "ruokalista", "olutta", 1, "15.12.2026", true),
            Task(2, "kotitehtävät", "kotlinia", 1, "04.12.2026", true),
            Task(3, "siivous", "kylppäri", 1, "15.06.2026", false),
            Task(4, "pelaaminen", "hk", 1, "22.12.2025", false),
            Task(5, "kuntosali", "rinta, taljat, kädet", 1, "17.12.2025", false)
        )
    }


    fun showDone()
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
    }
    fun addTask(uusitaski: Task)
    {
            Log.d("häh", "toomiiks1")
            _taskilistaState.value += uusitaski
            addTaskDialogVisible.value = false

    }
    fun removeTask(taskId: Int) //poistetaan taski sen sijainnin perusteella listasta
    {
        _taskilistaState.value.forEach { task ->
            if(taskId == task.id )
            {
                _taskilistaState.value -= task
            }
        }
        _valittutaski.value = null
    }
    fun toggleDone(uiListPositionIndex: Int) //muutetaan done taskin sijainnin perusteella listasta
    {
        _taskilistaState.value = _taskilistaState.value.mapIndexed { index, item ->
            if (uiListPositionIndex == index) {
                item.copy(done = !item.done)
            } else {
                item
            }
        }
    }
    fun sortByDate()
    {
        _taskilistaState.value = _taskilistaState.value.sortedBy{it.dueDate.substring(0, 2)} //sorttaillaan ekaks päivät, sitten kuut ja lopuks vuoden mukaan
        _taskilistaState.value = _taskilistaState.value.sortedBy{it.dueDate.substring(3, 5)}
        _taskilistaState.value = _taskilistaState.value.sortedBy{it.dueDate.substring(6, 10)}

    }
    fun isValidText(text: String): Boolean {
        return text.matches(Regex("^(?:[01]?[0-9]|2[0-3]).[0-5]?[0-9](?:.[0-5]?[0-9]?[0-9]?[0-9])?\$"))
    }
    fun updateTask(updated: Task)
    {
        _taskilistaState.value = _taskilistaState.value.map {
            if (it.id == updated.id) updated else it
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