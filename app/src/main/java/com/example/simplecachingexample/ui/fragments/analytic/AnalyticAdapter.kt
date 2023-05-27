package com.example.simplecachingexample.ui.fragments.analytic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecachingexample.R
import com.example.simplecachingexample.data.*
import com.example.simplecachingexample.databinding.AnalyticItemBinding
import com.example.simplecachingexample.databinding.CurrencyItemBinding

class AnalyticAdapter(
//    private val onLikeClickListener: (AnalyticDb) -> Unit
) :
    ListAdapter<CurrencyDb, AnalyticAdapter.AnalyticViewHolder>(CurrencyComparator()) {

    var onItemClick: ((CurrencyDb) -> Unit)? = null
    inner class AnalyticViewHolder(
        private val binding: AnalyticItemBinding,
//        private val onLikeClickListener: (AnalyticDb) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: CurrencyDb) {
            binding.apply {
                tvName.text = currency.key
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyticViewHolder {
        val binding =
            AnalyticItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnalyticViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnalyticViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class CurrencyComparator : DiffUtil.ItemCallback<CurrencyDb>() {
        override fun areItemsTheSame(oldItem: CurrencyDb, newItem: CurrencyDb) =
            oldItem.value == newItem.value

        override fun areContentsTheSame(oldItem: CurrencyDb, newItem: CurrencyDb) =
            oldItem == newItem
    }

}