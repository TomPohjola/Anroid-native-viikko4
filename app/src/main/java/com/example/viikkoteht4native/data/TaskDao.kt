package com.example.viikkoteht4native.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY dueDate DESC")
    fun getTasksOrdered(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): Task?

    @Query("SELECT * FROM tasks WHERE done = :completed")
    fun getTasksByStatus(completed: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :searchQuery || '%'")
    fun searchTasks(searchQuery: String): Flow<List<Task>>

    @Update
    suspend fun update(task: Task)

    @Query("UPDATE tasks SET done = :completed WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Int, completed: Boolean)

    @Query("UPDATE tasks SET description = :des, title = :title WHERE id = :taskId")
    suspend fun updateTask(taskId: Int, title: String, des: String)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks WHERE done = 1")
    suspend fun deleteCompletedTasks()

    @Query("SELECT COUNT(*) FROM tasks WHERE done = 0")
    fun getPendingTaskCount(): Flow<Int>
}