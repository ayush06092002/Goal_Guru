package com.who.goalguru.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.who.goalguru.models.ToDo
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ToDoDao{
    @Query("SELECT * FROM todo_table")
    fun getAllToDos(): Flow<List<ToDo>>

    @Query("UPDATE todo_table SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateIsCompleted(id: UUID, isCompleted: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDo)

    @Query("DELETE FROM todo_table WHERE id = :id")
    suspend fun deleteToDo(id: UUID)

    @Update
    suspend fun updateToDo(toDo: ToDo)

}