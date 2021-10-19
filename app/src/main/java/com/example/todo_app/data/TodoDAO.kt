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
}