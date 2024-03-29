package com.example.simplecachingexample.features.currencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecachingexample.R
import com.example.simplecachingexample.data.*
import com.example.simplecachingexample.databinding.CurrencyItemBinding

class CurrencyAdapter(private val onLikeClickListener: (CurrencyDb) -> Unit) :
    ListAdapter<CurrencyDb, CurrencyAdapter.CurrencyViewHolder>(CurrencyComparator()) {

    var onItemClick: ((CurrencyDb) -> Unit)? = null
    inner class CurrencyViewHolder(
        private val binding: CurrencyItemBinding,
        private val onLikeClickListener: (CurrencyDb) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: CurrencyDb) {
            binding.apply {
                tvName.text = currency.key
                tvType.text = currency.value.toString()

                val drawable = if (currency.isFavorite) {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_24)
                } else {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_border_24)
                }

                ibFavorite.setImageDrawable(drawable)

                ibFavorite.setOnClickListener {
                    onLikeClickListener(currency)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding, onLikeClickListener)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
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