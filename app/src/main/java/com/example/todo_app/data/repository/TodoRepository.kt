package com.example.todo_app.data.repository

import androidx.lifecycle.LiveData
import com.example.todo_app.data.TodoDAO
import com.example.todo_app.data.models.TodoData

class TodoRepository(private val todoDAO: TodoDAO) {

    val getAllData : LiveData<List<TodoData>> = todoDAO.getAllData()

    suspend fun insertData(todoData: TodoData){
        todoDAO.insertData(todoData)
    }
}