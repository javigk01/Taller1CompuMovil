package com.example.taller1compumovil.LogicaActivities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1compumovil.databinding.ActivityRecomendationsBinding
import org.json.JSONObject
import kotlin.random.Random

class RecomendationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecomendationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        val favoritos = MainActivity.favoritos
        if (favoritos.isEmpty()) {
            sinohayNA()
            return
        }

        val categoriaMasFrecuente = favoritos
            .map { it.optString("categoria") }
            .filter { it.isNotBlank() }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key

        if (categoriaMasFrecuente.isNullOrBlank()) {
            sinohayNA()
            return
        }

        val candidatos = favoritos.filter { it.optString("categoria") == categoriaMasFrecuente }
        if (candidatos.isEmpty()) {
            sinohayNA()
            return
        }

        val recomendado: JSONObject = candidatos[Random.nextInt(candidatos.size)]
        binding.txtNombre.text = recomendado.getString("nombre")
        binding.txtPais.text = recomendado.getString("pais")
        binding.txtCategoria.text = recomendado.getString("categoria")
        binding.txtActividad.text = recomendado.getString("descripcion")
        binding.txtPrecio.text = "USD ${recomendado.getInt("precio")}"

    }
    private fun sinohayNA() {
        binding.txtNombre.text = "NA"
        binding.txtPais.text = "NA"
        binding.txtCategoria.text = "NA"
        binding.txtActividad.text = "NA"
        binding.txtPrecio.text = "NA"
    }
}
