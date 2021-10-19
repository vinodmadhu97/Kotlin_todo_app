package com.example.todo_app.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.todo_app.R
import com.example.todo_app.data.models.Priority

class SharedVIewModel(application: Application) : AndroidViewModel(application) {

    val listener : AdapterView.OnItemSelectedListener= object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0-> {(parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.red)) }

                1-> {(parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.yellow)) }

                2-> {(parent!!.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                    R.color.green)) }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }


    }

    fun verifyUserData(title:String,description:String): Boolean{
        val isVerify = when{
            TextUtils.isEmpty(title.trim { it <= ' ' }) -> false
            TextUtils.isEmpty(description.trim { it <= ' ' }) -> false
            else -> true
        }

        return isVerify
    }

    fun parsePriorityObject(priority:String): Priority {
        return when(priority){
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW
            else -> Priority.LOW
        }
    }
}