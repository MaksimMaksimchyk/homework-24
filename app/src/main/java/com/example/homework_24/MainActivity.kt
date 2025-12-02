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
import com.example.homework_24.databinding.ActivityMainBinding
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
        viewModel.dataFromServer.observe(this) { result ->
            binding.resultView.text = result.toString()
            notLoadingUIState()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                isLoadingUIState()
            }
            else {
                notLoadingUIState()
            }
        }

    }

    private fun setupListeners() {
        binding.loadButton.setOnClickListener {
            isLoadingUIState()
            viewModel.getResult()
        }
    }

    private fun notLoadingUIState() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.loadButton.isClickable = true
        binding.loadButton.focusable = View.FOCUSABLE
        binding.loadButton.backgroundTintList = null
    }

    private fun isLoadingUIState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.loadButton.isClickable = false
        binding.loadButton.focusable = View.NOT_FOCUSABLE
        binding.loadButton.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
        binding.loadButton.setBackgroundColor(Color.GRAY)
    }


}


