package com.example.todo_app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo_app.data.models.TodoData

@Dao
interface TodoDAO {

    @Query("SELECT * FROM TODO_TABLE ORDER BY todo_id ASC")
    fun getAllData():LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todoData: TodoData)

    @Update
    suspend fun updateData(todoData: TodoData)

    @Delete
    suspend fun deleteItem(todoData: TodoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE todo_title LIKE :searchValue ")
    fun searchData(searchValue:String) : LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN todo_priority LIKE 'H%' THEN 1 WHEN todo_priority LIKE 'M%' THEN 2 WHEN todo_priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority():LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN todo_priority LIKE 'L%' THEN 1 WHEN todo_priority LIKE 'M%' THEN 2 WHEN todo_priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority():LiveData<List<TodoData>>
}