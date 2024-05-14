package com.who.goalguru.repository

import com.who.goalguru.data.ToDoDao
import com.who.goalguru.models.ToDo
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {
    fun getAllToDos() : Flow<List<ToDo>> = toDoDao.getAllToDos()
    suspend fun insertToDo(toDo: ToDo) = toDoDao.insertToDo(toDo)
    suspend fun deleteToDo(id: UUID) = toDoDao.deleteToDo(id)
    suspend fun updateIsCompleted(id: UUID, isCompleted: Boolean) = toDoDao.updateIsCompleted(id, isCompleted)
    suspend fun updateToDo(toDo: ToDo) = toDoDao.updateToDo(toDo)
}