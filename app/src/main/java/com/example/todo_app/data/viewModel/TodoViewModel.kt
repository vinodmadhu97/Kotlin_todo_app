package com.example.todo_app.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todo_app.data.TodoDatabase
import com.example.todo_app.data.models.TodoData
import com.example.todo_app.data.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TodoViewModel(application: Application) : AndroidViewModel(application){

    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val repository : TodoRepository
    val getAllData : LiveData<List<TodoData>>
    val sortByHighPriority : LiveData<List<TodoData>>
    val sortByLowPriority : LiveData<List<TodoData>>

    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(todoData: TodoData){

        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(todoData)
        }
    }

    fun updateData(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(todoData)
        }

    }

    fun deleteItem(todoData: TodoData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(todoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchedData(searchedKey:String):LiveData<List<TodoData>> = repository.searchedData(searchedKey)



}

