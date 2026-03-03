package com.example.viikkoteht4native.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val orderedtasks: Flow<List<Task>> = taskDao.getTasksOrdered()
    val pendingTaskCount: Flow<Int> = taskDao.getPendingTaskCount()

    suspend fun insert(task: Task): Long {
        return taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    suspend fun toggleTaskStatus(taskId: Int, completed: Boolean) {
        Log.d("completed", "$completed")
        taskDao.updateTaskStatus(taskId, completed)
    }

    suspend fun upTask(taskId: Int, due: String, des: String) {
        taskDao.updateTask(taskId, due, des )
    }

    fun getTasksByStatus(completed: Boolean): Flow<List<Task>> {
        return taskDao.getTasksByStatus(completed)
    }

    fun searchTasks(query: String): Flow<List<Task>> {
        return taskDao.searchTasks(query)
    }
    fun orderTasks(): Flow<List<Task>> {
        return taskDao.getTasksOrdered()
    }

    suspend fun deleteCompletedTasks() {
        taskDao.deleteCompletedTasks()
    }
}