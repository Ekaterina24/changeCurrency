package com.example.simplecachingexample.ui.fragments.filter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.simplecachingexample.R
import com.example.simplecachingexample.databinding.FragmentFilterBinding
import com.example.simplecachingexample.databinding.FragmentHistoryBinding
import com.example.simplecachingexample.features.currencies.CurrencyViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private var mBinding: FragmentFilterBinding? = null
    private val binding get() = mBinding!!

    private var clickedOne = false
    private var clickedTwo = false
    private var clickedDate = false
    private var clickedAll = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFilterBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnShowDateRangePicker.setOnClickListener {
                clickedDate = true
                showDateRangePicker()
            }

            btnOneWeek.setOnClickListener {
                clickedOne = true
                oneWeek()
            }

            btnTwoWeek.setOnClickListener {
                clickedTwo = true
                twoWeek()
            }

            btnAll.setOnClickListener {
                clickedAll = true
            }

            btnApply.setOnClickListener {
                if (clickedDate) {
                    setFragmentResultListener("res") { key, bundle ->
                        val resultTime = bundle.getStringArrayList("bundle") as ArrayList<String>
                        Log.d("MY_TAG", "resultTime: $resultTime")
                        findNavController().navigate(R.id.action_filterFragment_to_historyFragment, bundle)
                    }
                } else if (clickedOne) {
                    setFragmentResultListener("oneKey") { key, bundle ->
                        val resultTime = bundle.getStringArrayList("oneTime") as ArrayList<String>
                        Log.d("MY_TAG", "resultTime: $resultTime")
                        findNavController().navigate(R.id.action_filterFragment_to_historyFragment, bundle)
                    }
                } else if (clickedTwo) {
                    setFragmentResultListener("twoKey") { key, bundle ->
                        val resultTime = bundle.getStringArrayList("twoTime") as ArrayList<String>
                        Log.d("MY_TAG", "resultTime: $resultTime")
                        findNavController().navigate(R.id.action_filterFragment_to_historyFragment, bundle)
                    }
                } else if (clickedAll) {
                    findNavController().navigate(R.id.action_filterFragment_to_historyFragment)
                }
            }
        }
    }

    private fun showDateRangePicker() {

        val dateRangePicker = MaterialDatePicker.Builder
            .dateRangePicker()
            .setTitleText("Select Date")
            .build()

        dateRangePicker.show(
            requireActivity().supportFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { datePicked ->
            val startDate = (datePicked.first / 1000).toString()
            val endDate = (datePicked.second / 1000).toString()

            val time = getTime(startDate, endDate)
            setFragmentResult("res", bundleOf("bundle" to time))

        }
    }

    private fun oneWeek() {
        val second = (System.currentTimeMillis() / 1000)
        val first = second - (86400 * 7)

        val oneTime = getTime(second.toString(), first.toString())
        setFragmentResult("oneKey", bundleOf("oneTime" to oneTime))
    }

    private fun twoWeek() {
        val second = (System.currentTimeMillis() / 1000)
        val first = second - (86400 * 7 * 2)

        val twoTime = getTime(second.toString(), first.toString())
        setFragmentResult("twoKey", bundleOf("twoTime" to twoTime))
    }

    private fun getTime(first: String, second: String): ArrayList<String> {
        val list = arrayListOf(first, second)
        return list
    }

}