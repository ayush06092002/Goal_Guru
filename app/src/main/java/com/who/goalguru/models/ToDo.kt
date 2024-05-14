package com.who.goalguru.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.UUID


@Entity(tableName = "todo_table")
data class ToDo (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "task")
    var task: String,

    @ColumnInfo(name = "isCompleted")
    var isCompleted: Boolean,

    @ColumnInfo(name = "isImportant")
    var isImportant: Boolean,

    @ColumnInfo(name = "dueDate")
    var dueDate: LocalDate?
)