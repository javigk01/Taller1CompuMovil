package com.example.taller1compumovil

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1compumovil.databinding.ActivityDetailBinding
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val destinoJson = intent.getStringExtra("destino")
        setupDestinationDetails(destinoJson)
        setupFavoriteButton()
    }

    private fun setupDestinationDetails(destinoJson: String?) {
        try {
            destinoJson?.let {
                val destino = JSONObject(it)
                binding.txtNombre.text = destino.getString("nombre")
                binding.txtPais.text = destino.getString("pais")
                binding.txtCategoria.text = destino.getString("categoria")
                binding.txtDescripcion.text = destino.getString("descripcion")
                binding.txtPrecio.text = "USD ${destino.getInt("precio")}"
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al cargar los detalles", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFavoriteButton() {
        binding.btnAddFavorite.setOnClickListener {
            try {
                binding.btnAddFavorite.isEnabled = false
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}