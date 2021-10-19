package com.example.todo_app.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.todo_app.R
import com.example.todo_app.data.models.Priority
import com.example.todo_app.databinding.FragmentUpdateBinding
import com.example.todo_app.fragments.SharedVIewModel


class UpdateFragment : Fragment() {

    private lateinit var mBinding: FragmentUpdateBinding
    //updateFragmentArgs is auto generated class by the safe args
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedVIewModel : SharedVIewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        mBinding = FragmentUpdateBinding.inflate(layoutInflater,container,false)

        mBinding.currentTitleEt.setText(args.current.title)
        mBinding.currentDescriptionEt.setText(args.current.description)
        mBinding.currentPrioritiesSpinner.setSelection(parsePriority(args.current.priority))
        mBinding.currentPrioritiesSpinner.onItemSelectedListener = mSharedVIewModel.listener

        return mBinding.root
    }

    private fun parsePriority(priority: Priority):Int{
        return when(priority){
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }


}