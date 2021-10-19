package com.example.todo_app.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todo_app.R
import com.example.todo_app.data.models.Priority
import com.example.todo_app.data.models.TodoData
import com.example.todo_app.data.viewModel.TodoViewModel
import com.example.todo_app.databinding.FragmentAddBinding
import com.example.todo_app.fragments.SharedVIewModel


class AddFragment : Fragment() {

    private lateinit var mBinding : FragmentAddBinding
    private val todoViewModel : TodoViewModel by viewModels()
    private val sharedViewModel : SharedVIewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        mBinding = FragmentAddBinding.inflate(inflater,container,false)

        mBinding.prioritiesSpinner.onItemSelectedListener =  sharedViewModel.listener
        return mBinding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add -> insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb(){
        val title = mBinding.titleEt.text.toString()
        val priority = mBinding.prioritiesSpinner.selectedItem.toString()
        val description = mBinding.descriptionEt.text.toString()

        val isValid = sharedViewModel.verifyUserData(title,description)

        if(isValid){
            val todoData = TodoData(
                0,
                title,
                sharedViewModel.parsePriorityObject(priority),
                description
            )

            todoViewModel.insertData(todoData)
            Toast.makeText(requireActivity(),"Insert successful",Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(requireActivity(),"please fill out the all form files",Toast.LENGTH_SHORT).show()
        }


    }




}