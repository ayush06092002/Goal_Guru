package com.who.goalguru.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.who.goalguru.models.ToDo
import com.who.goalguru.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel(){
    private val _toDoList = MutableStateFlow<List<ToDo>>(emptyList())
    val toDoList = _toDoList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getAllToDos().distinctUntilChanged().collect{
                if(it.isEmpty()){
                    repository.insertToDo(ToDo(task = "Welcome to GoalGuru", isCompleted = false, isImportant = false, dueDate = null))
                } else {
                    _toDoList.value = it
                }
            }
        }
    }

    fun addToDoTask(toDo: ToDo){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertToDo(toDo)
        }
    }

    fun updateToDoTask(toDo: ToDo){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateToDo(toDo)
        }
    }

    fun deleteToDoTask(id: UUID){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteToDo(id)
        }
    }

    fun updateIsCompleted(id: UUID, isCompleted: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateIsCompleted(id, isCompleted)
        }
    }
}