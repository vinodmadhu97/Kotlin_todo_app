package com.example.todo_app.data

import androidx.room.TypeConverter
import com.example.todo_app.data.models.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority):String{
        return priority.name
    }
    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}