package com.example.simplecachingexample.ui.fragments.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecachingexample.data.HistoryModel
import com.example.simplecachingexample.databinding.HistoryItemBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter: ListAdapter<HistoryModel, HistoryAdapter.HistoryViewHolder>(HistoryComparator()) {

    inner class HistoryViewHolder(
        private val binding: HistoryItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryModel) {
            binding.apply {
                tvFirstKey.text = item.firstKey
                tvFirstValue.text = item.firstValue.toString()

                tvSecondKey.text = item.secondKey
                tvSecondValue.text = item.secondValue.toString()

                tvDateTime.text = getDateTime(item.date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
                holder.bind(currentItem)
        }

    }

    class HistoryComparator : DiffUtil.ItemCallback<HistoryModel>() {
        override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel) =
            oldItem.firstValue == newItem.firstValue

        override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel) =
            oldItem == newItem
    }

    private fun getDateTime(time: Long): String? {
        try {
            val sdf = SimpleDateFormat("HH:mm:ss dd/MM/yyyy ")
            val netDate = Date(time * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}