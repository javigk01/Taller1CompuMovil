package com.example.taller1compumovil

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1compumovil.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupButtons()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategories.adapter = adapter
    }

    private fun setupButtons() {
        binding.btnExplore.setOnClickListener {
            val selectedCategory = binding.spinnerCategories.selectedItem.toString()
            val intent = Intent(this, ExploreActivity::class.java)
            intent.putExtra("categoria", selectedCategory)
            startActivity(intent)
        }
    }
}