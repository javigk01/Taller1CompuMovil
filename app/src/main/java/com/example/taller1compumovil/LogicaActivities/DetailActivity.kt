package com.example.taller1compumovil.LogicaActivities

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
        setupFavoriteButton(destinoJson)
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

    private fun setupFavoriteButton(destinoJson: String?) {
        binding.btnAddFavorite.setOnClickListener {
            try {
                val jsonStr = destinoJson ?: return@setOnClickListener
                val destino = JSONObject(jsonStr)
                val nombre = destino.getString("nombre")

                val yaExiste = MainActivity.favoritos.any { it.optString("nombre") == nombre }
                if (!yaExiste)
                {
                    MainActivity.favoritos.add(destino)
                    Toast.makeText(this, "Destino añadido a favoritos", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "Ya lo tenias en favoritos tontin", Toast.LENGTH_SHORT).show()
                }
                binding.btnAddFavorite.isEnabled = false
            }
            catch (e: Exception)
            {
                Toast.makeText(this, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}