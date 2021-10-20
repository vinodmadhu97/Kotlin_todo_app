package com.example.todo_app.data.repository

import androidx.lifecycle.LiveData
import com.example.todo_app.data.TodoDAO
import com.example.todo_app.data.models.TodoData

class TodoRepository(private val todoDAO: TodoDAO) {

    val getAllData : LiveData<List<TodoData>> = todoDAO.getAllData()

    suspend fun insertData(todoData: TodoData){
        todoDAO.insertData(todoData)
    }

    suspend fun updateItem(todoData: TodoData){
        todoDAO.updateData(todoData)
    }

    suspend fun deleteItem(todoData: TodoData){
        todoDAO.deleteItem(todoData)
    }

    suspend fun deleteAll(){
        todoDAO.deleteAll()
    }

    fun searchedData(searchKey:String) : LiveData<List<TodoData>> = todoDAO.searchData(searchKey)

    val sortByHighPriority : LiveData<List<TodoData>> = todoDAO.sortByHighPriority()
    val sortByLowPriority : LiveData<List<TodoData>> = todoDAO.sortByLowPriority()


}