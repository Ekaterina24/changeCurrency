package com.example.simplecachingexample.features.currencies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplecachingexample.R
import com.example.simplecachingexample.data.*
//import com.example.simplecachingexample.data.FavoriteCurrencyDb
import com.example.simplecachingexample.databinding.CurrencyItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyAdapter(
    private val database: CurrencyDatabase
) :
    ListAdapter<CurrencyDb, CurrencyAdapter.CurrencyViewHolder>(CurrencyComparator()) {

    private var fl = true

    inner class CurrencyViewHolder(private val binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val favoriteButton = binding.ibFavorite
        fun bind(currency: CurrencyDb) {
            binding.apply {
                tvName.text = currency.key
                tvType.text = currency.value.toString()

                if (currency.isFavorite) {
                    ibFavorite.setImageResource(R.drawable.ic_favorite_24)
                } else {
                    ibFavorite.setImageResource(R.drawable.ic_favorite_border_24)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }

        holder.favoriteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val item = currentItem.toFavoriteCurrency()
                if (fl) {
                    holder.favoriteButton.setImageResource(R.drawable.ic_favorite_border_24)
                    database.favoriteCurrencyDao().delete(item)
                    database.currencyDao().update(CurrencyDb(item.key, item.value, item.timestamp, false))
                    fl = false
                } else {
                    holder.favoriteButton.setImageResource(R.drawable.ic_favorite_24)
                    database.favoriteCurrencyDao().insert(item)
                    database.currencyDao().update(CurrencyDb(item.key, item.value, item.timestamp, true))
                    fl = true
                }
            }

        }

    }

    class CurrencyComparator : DiffUtil.ItemCallback<CurrencyDb>() {
        override fun areItemsTheSame(oldItem: CurrencyDb, newItem: CurrencyDb) =
            oldItem.value == newItem.value

        override fun areContentsTheSame(oldItem: CurrencyDb, newItem: CurrencyDb) =
            oldItem == newItem
    }

}