package com.example.todo_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo_app.data.models.TodoData

@Database(entities = [TodoData::class],version = 1,exportSchema = false)
@TypeConverters(PriorityConverter::class)
abstract class TodoDatabase() : RoomDatabase() {
    abstract fun todoDao() : TodoDAO

    companion object{
        @Volatile
        private var INSTANCE : TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }else{
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo_database"
                    ).build()

                    INSTANCE = instance
                    return instance
                }


            }
        }
    }

}