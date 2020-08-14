package com.example.to_doappadvanced.data.repository

import androidx.lifecycle.LiveData
import com.example.to_doappadvanced.data.ToDoDao
import com.example.to_doappadvanced.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllDataComplete: LiveData<List<ToDoData>> = toDoDao.getAllDataComplete()
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()
    val getAllDataCompleted: LiveData<List<ToDoData>> = toDoDao.getAllDataCompleted()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>> {
        return toDoDao.searchDatabase(searchQuery)
    }

}