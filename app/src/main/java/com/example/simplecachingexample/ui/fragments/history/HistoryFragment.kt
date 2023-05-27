package com.example.simplecachingexample.ui.fragments.history

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.example.simplecachingexample.R
import com.example.simplecachingexample.databinding.FragmentHistoryBinding
import com.example.simplecachingexample.features.currencies.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var mBinding: FragmentHistoryBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keyArg = arguments?.getStringArrayList("bundle")
        val keyArg2 = arguments?.getStringArrayList("oneTime")
        val keyArg3 = arguments?.getStringArrayList("twoTime")
        val oneDay = 86400

        var firstTime: Long? = null
        var secondTime: Long? = null

        if (keyArg != null) {
            firstTime = keyArg.get(0)?.toLong()
            secondTime = keyArg.get(1)?.toLong()
        } else if (keyArg2 != null) {
            firstTime = keyArg2.get(1)?.toLong()
            secondTime = keyArg2.get(0)?.toLong()
            Log.d("MY_TAG", "oneWeek: $firstTime $secondTime")
        } else if (keyArg3 != null) {
            firstTime = keyArg3.get(1)?.toLong()
            secondTime = keyArg3.get(0)?.toLong()
        }

        val historyAdapter = HistoryAdapter()


        binding.apply {

            recyclerView.apply {
                adapter = historyAdapter
            }

            if (firstTime != null) {
                tvDate.text = "${firstTime?.let { getDateTime(it) }} - ${secondTime?.let { getDateTime(it) }}"
            }

            viewModel.historyItems.observe(viewLifecycleOwner) { result ->

                Log.d("MY_TAG", "historyFragment: $result")
                historyAdapter.submitList(
                    if (firstTime != null) {
                        result.filter {
                            (it.date >= firstTime!!.toLong()) && (it.date <= secondTime!!.toLong() + oneDay)
                        }.sortedByDescending { it.date }
                    } else {
                        result.sortedByDescending {
                            it.date
                        }
                    }

                )

            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> findNavController().navigate(R.id.action_historyFragment_to_filterFragment)
        }
        return true
    }

    private fun getDateTime(time: Long): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy ")
            val netDate = Date(time * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}