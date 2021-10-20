package com.example.todo_app.fragments.list.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.data.models.Priority
import com.example.todo_app.data.models.TodoData
import com.example.todo_app.databinding.RowLayoutBinding
import com.example.todo_app.fragments.list.ListFragmentDirections

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var dataList = emptyList<TodoData>()

    class ListViewHolder(binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.titleTxt
        val tvDescription = binding.descriptionTxt
        val cvPriorityIndicator = binding.priorityIndicator
        val listItemView = binding.rowBackground

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.tvTitle.text = dataList[position].title
        holder.tvDescription.text = dataList[position].description
        val priority = dataList[position].priority

        holder.listItemView.setOnClickListener {

            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }



        when (priority) {
            Priority.HIGH -> holder.cvPriorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )
            Priority.MEDIUM -> holder.cvPriorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.yellow
                )
            )
            Priority.LOW -> holder.cvPriorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(todoDataList: List<TodoData>){


        //notifyDataSetChanged()
        // notify data set changed funtion re-render all of the item when data changing. that will reduce performance
        //of the app avoid to the we can use diff util
        val todoDataDiffUtil = TodoDiffUtil(dataList,todoDataList)
        val todoDiffUtilResult = DiffUtil.calculateDiff(todoDataDiffUtil)
        this.dataList = todoDataList
        todoDiffUtilResult.dispatchUpdatesTo(this)
    }
}