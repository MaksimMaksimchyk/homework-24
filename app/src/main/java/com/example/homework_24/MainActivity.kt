package com.example.homework_24

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.homework_24.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is MainActivityViewModel.UIstate.Empty -> showEmpty(state.message)
                        is MainActivityViewModel.UIstate.Error -> showResult(state.message)
                        is MainActivityViewModel.UIstate.Loading -> showLoading(state.message)
                        is MainActivityViewModel.UIstate.Success -> showResult(state.result)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.loadButton.setOnClickListener {
            viewModel.getResult()
        }
    }

    private fun showEmpty(message: String) {
        binding.resultView.text = message
    }

    private fun showResult(result: String) {
        binding.resultView.text = result
        binding.progressBar.visibility = View.INVISIBLE
        binding.loadButton.isClickable = true
        binding.loadButton.backgroundTintList = null
    }

    private fun showLoading(message: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.loadButton.isClickable = false
        binding.loadButton.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
        binding.loadButton.setBackgroundColor(Color.GRAY)
        binding.resultView.text = message
    }

}


