package com.example.todo_app.fragments.update

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo_app.R
import com.example.todo_app.data.models.Priority
import com.example.todo_app.data.models.TodoData
import com.example.todo_app.data.viewModel.TodoViewModel
import com.example.todo_app.databinding.FragmentUpdateBinding
import com.example.todo_app.fragments.SharedVIewModel


class UpdateFragment : Fragment() {

    private lateinit var mBinding: FragmentUpdateBinding

    //updateFragmentArgs is auto generated class by the safe args
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedVIewModel: SharedVIewModel by viewModels()
    private val mTodoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        mBinding = FragmentUpdateBinding.inflate(layoutInflater, container, false)

        mBinding.currentTitleEt.setText(args.current.title)
        mBinding.currentDescriptionEt.setText(args.current.description)
        mBinding.currentPrioritiesSpinner.setSelection(mSharedVIewModel.parsePriorityToInt(args.current.priority))
        mBinding.currentPrioritiesSpinner.onItemSelectedListener = mSharedVIewModel.listener

        return mBinding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setPositiveButton("Yes"){_,_ ->
            mTodoViewModel.deleteItem(args.current)
            Toast.makeText(requireActivity(),"${args.current.title} todo removed",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        alertDialogBuilder.setNegativeButton("No"){dialog,_ -> dialog.dismiss()}
        alertDialogBuilder.setTitle("Delete ${args.current.title}")
        alertDialogBuilder.setMessage("Are you Sure You want to Remove ${args.current.title} ?")

        alertDialogBuilder.create().show()

    }

    private fun updateItem() {
        val title = mBinding.currentTitleEt.text.toString()
        val description = mBinding.currentDescriptionEt.text.toString()
        val priority = mBinding.currentPrioritiesSpinner.selectedItem.toString()

        val validation = mSharedVIewModel.verifyUserData(title, description)

        if (validation) {
            val updateItem = TodoData(
                args.current.id,
                title,
                mSharedVIewModel.parsePriorityObject(priority),
                description
            )

            mTodoViewModel.updateData(updateItem)
            Toast.makeText(requireActivity(), "Update Successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }
    }


}