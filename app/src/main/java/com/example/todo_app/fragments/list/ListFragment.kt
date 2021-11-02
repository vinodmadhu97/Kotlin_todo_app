package com.example.todo_app.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todo_app.R
import com.example.todo_app.data.models.TodoData
import com.example.todo_app.data.viewModel.TodoViewModel
import com.example.todo_app.databinding.FragmentListBinding
import com.example.todo_app.fragments.list.adapter.ListAdapter
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(),SearchView.OnQueryTextListener {

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
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        recyclerView.itemAnimator = LandingAnimator().apply {
            addDuration = 300
        }
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
                listAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                //Toast.makeText(requireActivity(),"Successfully removed",Toast.LENGTH_SHORT).show()
                restoreDeleteData(viewHolder.itemView,itemToDelete,viewHolder.adapterPosition)
            }


        }
        val itemTouchHelper = ItemTouchHelper(swapToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteData(view:View,deletedItem:TodoData,position: Int){
        val snackBar = Snackbar.make(view,"Deleted ${deletedItem.title}",Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo"){
            viewModel.insertData(deletedItem)
            listAdapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete_all -> confirmDeleteAllItems()
            R.id.menu_priority_high -> viewModel.sortByHighPriority.observe(this, Observer {
                listAdapter.setData(it)
            })
            R.id.menu_priority_low -> viewModel.sortByLowPriority.observe(this, Observer {
                listAdapter.setData(it)
            })
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchThroughDatabase(query)
        }
        return true
    }



    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            searchThroughDatabase(newText)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchedData(searchQuery).observe(this, Observer {
            it?.let {
                listAdapter.setData(it)
            }
        })
    }


}