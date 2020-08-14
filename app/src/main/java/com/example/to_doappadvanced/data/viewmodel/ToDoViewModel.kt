package com.example.to_doappadvanced.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.to_doappadvanced.data.ToDoDatabase
import com.example.to_doappadvanced.data.models.ToDoData
import com.example.to_doappadvanced.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(
        application
    ).toDoDao()
    private val repository: ToDoRepository

    val getAllDataComplete: LiveData<List<ToDoData>>
    val getAllDataCompleted: LiveData<List<ToDoData>>
    val sortByHighPriority: LiveData<List<ToDoData>>
    val sortByLowPriority: LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDao)
        getAllDataComplete = repository.getAllDataComplete
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
        getAllDataCompleted = repository.getAllDataCompleted
    }

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteItem(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(toDoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>{
        return repository.searchDatabase(searchQuery)
    }
}