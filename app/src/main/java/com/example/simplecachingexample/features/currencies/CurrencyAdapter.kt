package com.example.simplecachingexample.features.currencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecachingexample.data.CurrencyDb
import com.example.simplecachingexample.databinding.CurrencyItemBinding

class CurrencyAdapter: ListAdapter<CurrencyDb, CurrencyAdapter.CurrencyViewHolder>(CurrencyComparator()) {

    class CurrencyViewHolder(private val binding: CurrencyItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

                fun bind(currency: CurrencyDb) {
                    binding.apply {
                            tvName.text = currency.key
                            tvType.text = currency.value.toString()
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class CurrencyComparator: DiffUtil.ItemCallback<CurrencyDb>() {
        override fun areItemsTheSame(oldItem: CurrencyDb, newItem: CurrencyDb) =
            oldItem.value == newItem.value

        override fun areContentsTheSame(oldItem: CurrencyDb, newItem: CurrencyDb) =
            oldItem == newItem
    }
}