package com.example.simplecachingexample.ui.fragments.change

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.simplecachingexample.R
import com.example.simplecachingexample.databinding.FragmentChangeBinding
import com.example.simplecachingexample.databinding.FragmentListBinding
import com.example.simplecachingexample.features.currencies.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class ChangeFragment : Fragment() {

    private var mBinding: FragmentChangeBinding? = null
    private val binding get() = mBinding!!
    private val viewModel: CurrencyViewModel by viewModels()

    var course: Double? = null

    var keyArg: String? = null
    var valueArg: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentChangeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editOne = binding.tiFirstCurrency
        val editTwo = binding.tiSecondCurrency

        val editOneRes = binding.tiFirstCurrency
        val editTwoRes = binding.tiSecondCurrency

        editOne.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                editOne.removeTextChangedListener(textWatcher2)
                editOne.addTextChangedListener(textWatcher)
            }
        }

        editTwo.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                editTwo.removeTextChangedListener(textWatcher)
                editTwo.addTextChangedListener(textWatcher2)
            }
        }

        binding.apply {
            keyArg = arguments?.getString("key")
            valueArg = arguments?.getDouble("value").toString()
            tvFirstCurrency.text = keyArg
            tiFirstCurrency.setText("1")
        }

        viewModel.favoriteCurrencies.observe(viewLifecycleOwner) { result ->
            val firstKey = result.first().key
            val firstValue = result.first().value.toString()

            course = valueArg?.toDouble()?.div(firstValue.toDouble())
            Log.d("MY_TAG", "course: $course")

            binding.apply {
                tvSecondCurrency.text = firstKey
                tiSecondCurrency.setText((((1 / course!!) * 100.0).roundToInt() / 100.0).toString())

                btnChange.setOnClickListener {
                    viewModel.insertHistoryItem(keyArg.toString(), editOneRes.text.toString().toDouble(), firstKey, editTwoRes.text.toString().toDouble())
                    findNavController().navigate(R.id.action_changeFragment_to_listFragment)
                }
            }

        }
    }

    val textWatcher = object : TextWatcher {
        override
        fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2:Int) {}

        override
        fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override
        fun afterTextChanged(editable: Editable?) {
            val editOne = binding.tiFirstCurrency
            val editTwo = binding.tiSecondCurrency
            if (editable != null && !editable.toString().equals("")
            ) {
                val edit = editable.hashCode()
                if (editOne.text.hashCode() == edit) {
                    editTwo.removeTextChangedListener(textWatcher2)
                    editOne.removeTextChangedListener(this)

                    if (editOne.text.toString().isNotEmpty()
                        && (editOne.text.toString()[editOne.text.toString().lastIndex].toString() != ".")
                        && (editOne.text.toString()[editOne.text.toString().lastIndex].toString() != ",")
                        && (editOne.text.toString().indexOf(".").toString() != "0")
                        && (editOne.text.toString().indexOf(",").toString() != "0")
                    ) {
                        val res = (editOne.text.toString().toDouble() / course!!)
                        Log.d("MY_TAG", "res2: ${res}")
                        val roundoff = ((res * 100.0).roundToInt() / 100.0)
                        editTwo.setText(roundoff.toString())
                    }
                    editOne.addTextChangedListener(this)
                }
            }
        }
    }

    val textWatcher2 = object : TextWatcher {
        override
        fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2:Int) {}

        override
        fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        @SuppressLint("SuspiciousIndentation")
        override
        fun afterTextChanged(editable: Editable?) {
            val editOne = binding.tiFirstCurrency
            val editTwo = binding.tiSecondCurrency
            if (editable != null && !editable.toString().equals("")
            ) {
                val edit2 = editable.hashCode()
                if (editTwo.text.hashCode() == edit2) {
                    editTwo.removeTextChangedListener(this)

                    if (editTwo.text.toString().isNotEmpty()
                        && (editTwo.text.toString()[editTwo.text.toString().lastIndex].toString() != ".")
                        && (editTwo.text.toString()[editTwo.text.toString().lastIndex].toString() != ",")
                        && (editTwo.text.toString().indexOf(".").toString() != "0")
                        && (editTwo.text.toString().indexOf(",").toString() != "0")
                    ) {
                        val res2 = (editTwo.text.toString().toDouble() * course!!)
                        val roundoff2 = ((res2 * 100.0).roundToInt() / 100.0)
                        Log.d("MY_TAG", "rez: ${res2}")
                        editOne.setText(roundoff2.toString())
                    }
                    editTwo.addTextChangedListener(this)
                }
            }
        }
    }

}