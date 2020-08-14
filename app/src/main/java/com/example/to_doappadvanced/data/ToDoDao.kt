package com.example.to_doappadvanced.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.to_doappadvanced.data.models.ToDoData

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table WHERE complete = 0 ORDER by id ASC")
    fun getAllDataComplete(): LiveData<List<ToDoData>>

    @Query("SELECT * FROM todo_table WHERE complete = 1 ORDER by id ASC")
    fun getAllDataCompleted(): LiveData<List<ToDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 WHEN priority LIKE 'N%' THEN 4 END")
    fun sortByHighPriority():LiveData<List<ToDoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'N%' THEN 1 WHEN priority LIKE 'L%' THEN 2 WHEN priority LIKE 'M%' THEN 3 WHEN priority LIKE 'H%' THEN 4 END")
    fun sortByLowPriority():LiveData<List<ToDoData>>
}