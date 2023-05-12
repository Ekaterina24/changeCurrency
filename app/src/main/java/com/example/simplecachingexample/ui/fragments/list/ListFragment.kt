package com.example.simplecachingexample.ui.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.simplecachingexample.R
import com.example.simplecachingexample.data.CurrencyDatabase
import com.example.simplecachingexample.data.FavoriteCurrencyDao
import com.example.simplecachingexample.databinding.FragmentListBinding
import com.example.simplecachingexample.features.currencies.CurrencyAdapter
import com.example.simplecachingexample.features.currencies.CurrencyViewModel
import com.example.simplecachingexample.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var mBinding: FragmentListBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = viewModel.db
        val currencyAdapter = CurrencyAdapter(database)

        binding.apply {
            recyclerView.apply {
                adapter = currencyAdapter
            }

            viewModel.currencies.observe(viewLifecycleOwner) { result ->
                currencyAdapter.submitList(result.data)
                Log.d("MY_TAG", "onCreateListFragment: ${result.data}")
                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage
            }

//            viewModel.favoriteCurrencies.observe(viewLifecycleOwner) { result ->
////                currencyAdapter.submitList(result)
//                result.forEach { item ->
//                    item.favoriteButton.setImageResource(R.drawable.ic_favorite_24)
//                }
////                Log.d("MY_TAG", "onCreateListFragment: ${result.data}")
////                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
////                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
////                textViewError.text = result.error?.localizedMessage
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }


}