package com.example.todo_app.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.data.viewModel.TodoViewModel
import com.example.todo_app.databinding.FragmentListBinding
import com.example.todo_app.fragments.list.adapter.ListAdapter


class ListFragment : Fragment() {

    private lateinit var mBinding : FragmentListBinding
    private val listAdapter : ListAdapter by lazy { ListAdapter() }
    private val viewModel : TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentListBinding.inflate(inflater,container,false)

        mBinding.floatingActionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }



        //set Menu
        setHasOptionsMenu(true)

        //set recycler view
        val recyclerView = mBinding.rvTodo
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = GridLayoutManager(requireActivity(),2)
        swipeToDelete(recyclerView)

        viewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            if (data.isNotEmpty()){
                mBinding.rvTodo.visibility =View.VISIBLE
                mBinding.noDataView.visibility = View.GONE
                listAdapter.setData(data)
            }else{
                mBinding.rvTodo.visibility =View.GONE
                mBinding.noDataView.visibility = View.VISIBLE
            }


        })





        return mBinding.root
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swapToDeleteCallBack = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val itemToDelete = listAdapter.dataList[viewHolder.adapterPosition]
                viewModel.deleteItem(itemToDelete)
                Toast.makeText(requireActivity(),"Successfully removed",Toast.LENGTH_SHORT).show()
            }


        }
        val itemTouchHelper = ItemTouchHelper(swapToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete_all -> confirmDeleteAllItems()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteAllItems() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setPositiveButton("Yes"){_,_ ->
            viewModel.deleteAll()
            Toast.makeText(requireActivity(),"All todos deleted",Toast.LENGTH_SHORT).show()
        }
        alertDialogBuilder.setNegativeButton("No"){dialog,_ -> dialog.dismiss()}
        alertDialogBuilder.setTitle("Delete All")
        alertDialogBuilder.setMessage("Do you want to Delete All Items ? ")
        alertDialogBuilder.create().show()


    }


}