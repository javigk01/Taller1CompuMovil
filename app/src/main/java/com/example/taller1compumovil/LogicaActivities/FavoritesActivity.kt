package com.example.taller1compumovil.LogicaActivities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1compumovil.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFavoritesList()
    }

    private fun setupFavoritesList() {
        val nombres = MainActivity.favoritos.map { it.optString("nombre", "(Sin nombre)") }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombres)
        binding.listaFavoritos.adapter = adapter
        binding.listaFavoritos.emptyView = binding.emptyView
    }

}
