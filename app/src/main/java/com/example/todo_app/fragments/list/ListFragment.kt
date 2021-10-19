package com.example.todo_app.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_app.R
import com.example.todo_app.data.viewModel.TodoViewModel
import com.example.todo_app.databinding.FragmentListBinding


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

        viewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            listAdapter.setData(data)

        })


        return mBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)

    }


}