package com.example.viikkoteht4native.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "priority")
    val priority: Int,

    @ColumnInfo(name = "dueDate")
    val dueDate: String,

    @ColumnInfo(name = "done")
    val done: Boolean = false
)

// Tämä luo SQLite-taulun:
// CREATE TABLE tasks (
//     id INTEGER PRIMARY KEY AUTOINCREMENT,
//     title TEXT NOT NULL,
//     description TEXT NOT NULL,
//     is_completed INTEGER NOT NULL DEFAULT 0,
//     created_at INTEGER NOT NULL
// )