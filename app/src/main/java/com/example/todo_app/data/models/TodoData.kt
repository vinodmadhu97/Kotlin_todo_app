package com.example.todo_app.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    var id : Int,
    @ColumnInfo(name = "todo_title")
    var title : String,
    @ColumnInfo(name = "todo+priority")
    var priority: Priority,
    @ColumnInfo(name = "todo_description")
    var description : String
)