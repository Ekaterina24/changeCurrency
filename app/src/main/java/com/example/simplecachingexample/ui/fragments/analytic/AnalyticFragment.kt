package com.example.simplecachingexample.ui.fragments.analytic

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.androidplot.xy.*
import com.example.simplecachingexample.R
import com.example.simplecachingexample.databinding.FragmentAnalyticBinding
import com.example.simplecachingexample.databinding.FragmentListBinding
import com.example.simplecachingexample.features.currencies.CurrencyAdapter
import com.example.simplecachingexample.features.currencies.CurrencyViewModel
import com.example.simplecachingexample.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class AnalyticFragment : Fragment() {

    private var mBinding: FragmentAnalyticBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: CurrencyViewModel by viewModels()
    var bool = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAnalyticBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val analyticAdapter = AnalyticAdapter()

        binding.apply {
            recyclerView.apply {
                adapter = analyticAdapter
            }
            var keyGlobal: String? = null
            viewModel.currencies.observe(viewLifecycleOwner) { result ->
                analyticAdapter.submitList(result.data)

                analyticAdapter.onItemClick = {
                    bool = true
                    keyGlobal = it.key
                    val bundle = bundleOf(
                        "key" to it.key,
                        "value" to it.value
                    )
                    Log.d("MY_TAG", "bundle: $bundle")
                }
            }
//            plot.clear()
            btnApply.setOnClickListener {
                Log.d("MY_TAG", "keyGlobal: $keyGlobal")
                viewModel.analyticCurrencies.observe(viewLifecycleOwner) { result ->

                    val current: String?
                    if (bool) {
                        current = keyGlobal
                    } else {
                        current = "AOA"
                    }

                    val list = arrayListOf<Number>()
                    val list2 = arrayListOf<Number>()

                    result.map {
                        if (it.key == current) {

                            list.add(it.value)
                            list2.add(it.timestamp)
                        }
                    }
                    Log.d("MY_TAG", "listCurrent: $list")
                    Log.d("MY_TAG", "list2: $list2")
                    Log.d("MY_TAG", "resultAnalytic: $result")


                    val domainLabels = list.toList()
                    Log.d("MY_TAG", "domainLabels: $domainLabels")

                    val new: Array<Number> = list2.toTypedArray()
                    val series1Number = new


                    val series1 =
                        SimpleXYSeries(Arrays.asList(* series1Number), domainLabels, "Series 1");

                    Log.d("MY_TAG", "series1: ${series1}")

                    val series1Format = LineAndPointFormatter(Color.BLUE, Color.BLACK, null, null)
//                    val newArray: List<String> = series1Number.map { getDateTime(it.toLong()) }
//
//                    Log.d("MY_TAG", "series1NumberMap: ${newArray}")

                    series1Format.setInterpolationParams(
                        CatmullRomInterpolator.Params(
                            10,
                            CatmullRomInterpolator.Type.Centripetal
                        )
                    )

                    plot.addSeries(series1, series1Format)
                    plot.title.text = current
                    PanZoom.attach(plot)
                }

            }
//            viewModel.analyticCurrencies.observe(viewLifecycleOwner) { result ->
//                val current: String?
//                if (bool) {
//                    current = arguments?.getString("key")
////                    bool = false
//                } else {
//                    current = "AOA"
//                }
//
//                val list = arrayListOf<Number>()
//                val list2 = arrayListOf<Number>()
//
//                result.map {
//                    if (it.key == current) {
//
//                        list.add(it.value)
//                        list2.add(it.timestamp)
//                    }
//                }
//                Log.d("MY_TAG", "listCurrent: $list")
//                Log.d("MY_TAG", "list2: $list2")
//                Log.d("MY_TAG", "resultAnalytic: $result")
//
//
//                val domainLabels = list.toList()
//                Log.d("MY_TAG", "domainLabels: $domainLabels")
//
//                val new: Array<Number> = list2.toTypedArray()
//                val series1Number = new
//
//
//                val series1 =
//                    SimpleXYSeries(Arrays.asList(* series1Number), domainLabels, "Series 1");
//
//                Log.d("MY_TAG", "series1: ${series1}")
//
//                val series1Format = LineAndPointFormatter(Color.BLUE, Color.BLACK, null, null)
//                val newArray: List<String> = series1Number.map { getDateTime(it.toLong()) }
//
//                Log.d("MY_TAG", "series1NumberMap: ${newArray}")
//
//                series1Format.setInterpolationParams(
//                    CatmullRomInterpolator.Params(
//                        10,
//                        CatmullRomInterpolator.Type.Centripetal
//                    )
//                )
//
//                plot.addSeries(series1, series1Format)
//                plot.title.text = current
//                PanZoom.attach(plot)
//            }
        }

    }

    private fun getDateTime(time: Long): String {
        try {
            val sdf = SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
            val netDate = Date(time * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}