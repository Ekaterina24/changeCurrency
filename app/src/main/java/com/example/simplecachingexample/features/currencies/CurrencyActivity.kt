package com.example.simplecachingexample.features.currencies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplecachingexample.MAIN
import com.example.simplecachingexample.R
import com.example.simplecachingexample.databinding.ActivityCurrencyBinding
import com.example.simplecachingexample.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyActivity : AppCompatActivity() {

//    private val viewModel: CurrencyViewModel by viewModels()

    private var mBinding: ActivityCurrencyBinding? = null
    private val binding get() = mBinding!!
//    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
//        navController = Navigation.findNavController(this, R.id.nav_host)
        binding.bottomNavigationView.setupWithNavController(navController)
//        MAIN = this
//        navController = Navigation.findNavController(this, R.id.nav_host)
//        binding.bottomNavigationView.setupWithNavController(navController)
//        val currencyAdapter = CurrencyAdapter()

//        binding.apply {
//            recyclerView.apply {
//                adapter = currencyAdapter
//                layoutManager = LinearLayoutManager(this@CurrencyActivity)
//            }
//
//
//            viewModel.currencies.observe(this@CurrencyActivity) { result ->
//                currencyAdapter.submitList(result.data)
//                Log.d("MY_TAG", "onCreate: ${result.data}")
//////                Log.d("MY_TAG", "onCreate: ${result}")
//                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
//                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
//                textViewError.text = result.error?.localizedMessage
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}