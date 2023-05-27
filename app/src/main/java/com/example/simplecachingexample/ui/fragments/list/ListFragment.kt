package com.example.simplecachingexample.ui.fragments.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asFlow
import androidx.navigation.fragment.findNavController
import com.example.simplecachingexample.R
import com.example.simplecachingexample.data.AnalyticDb
import com.example.simplecachingexample.data.CurrencyDatabase
import com.example.simplecachingexample.data.FavoriteCurrencyDao
import com.example.simplecachingexample.databinding.FragmentListBinding
import com.example.simplecachingexample.features.currencies.CurrencyAdapter
import com.example.simplecachingexample.features.currencies.CurrencyViewModel
import com.example.simplecachingexample.ui.fragments.change.ChangeFragment
import com.example.simplecachingexample.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var mBinding: FragmentListBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentListBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currencyAdapter = CurrencyAdapter(
            onLikeClickListener = { currency ->
                viewModel.onLikeClicked(currency)
            }
        )

        binding.apply {
            recyclerView.apply {
                adapter = currencyAdapter
            }

//            viewModel.installFavorite().observe(viewLifecycleOwner) {
//                Log.d("MY_TAG", "installFavorite: ${it.data}")
//            }

//combine(viewModel.currencies.value, viewModel.analyticCurrencies.value) {
//
//}

            viewModel.currencies.observe(viewLifecycleOwner) { result ->
                currencyAdapter.submitList(result.data)

                currencyAdapter.onItemClick = {
                    val bundle = bundleOf(
                        "key" to it.key,
                        "value" to it.value
                    )
                    findNavController().navigate(R.id.action_listFragment_to_changeFragment, bundle)
                }

//                viewModel.analyticCurrencies.observe(viewLifecycleOwner) { result2 ->
//                    result.data?.map { i ->
////                        result2.map { k ->
////                            if (i.key == k.key) {
////                                val updated = mutableListOf<String>()
////                                updated.addAll(k.array)
////                                Log.d("MY_TAG", "itemArray: ${k.array}")
////                                updated.add(i.value.toString())
////                                Log.d("MY_TAG", "itemValue: ${i.value}")
////                                Log.d("MY_TAG", "updated: ${k} ${updated}")
////                                viewModel.updateAnalyticItem(AnalyticDb(k.key, updated))
////                            }
////                        }
//                        viewModel.insertAnalyticItem(AnalyticDb(i.key, listOf(i.value.toString())))
//                    }
//                }

                Log.d("MY_TAG", "onCreateListFragment: ${result.data}")
                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textViewError.text = result.error?.localizedMessage

            }


//            viewModel.fav()
//            viewModel.analyticCurrencies.observe(viewLifecycleOwner) {res->
//                Log.d("MY_TAG", "analyticList: $res")
////                res.map { i ->
////                    i.
////                }
//                Log.d("MY_TAG", "analyticItem: ${res.get(0).array}")
//
//            }

//            viewModel.favoriteCurrencies.observe(viewLifecycleOwner) {res ->
//                Log.d("MY_TAG", "onViewCreated: $res")
//            }


//            val itemList = viewModel.favoriteCurrencies.value
//            Log.d("MY_TAG", "favoriteCurrencies: $itemList")

//            viewModel.favoriteCurrencies.observe(viewLifecycleOwner) { result ->
////                currencyAdapter.submitList(result)
//                val firstItem = result.first().key
////                result.forEach { item ->
////                    item.favoriteButton.setImageResource(R.drawable.ic_favorite_24)
////                }
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