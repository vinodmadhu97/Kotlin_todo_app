package com.example.todo_app.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todo_app.R
import com.example.todo_app.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var mBinding : FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentListBinding.inflate(inflater,container,false)

        mBinding.floatingActionBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        mBinding.fragmentList.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        //set Menu
        setHasOptionsMenu(true)

        return mBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)

    }


}