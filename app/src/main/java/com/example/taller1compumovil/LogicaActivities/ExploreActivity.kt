package com.example.taller1compumovil.LogicaActivities

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1compumovil.Misc.loadJSON
import com.example.taller1compumovil.databinding.ActivityExploreBinding
import org.json.JSONObject

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoria = intent.getStringExtra("categoria") ?: "Todos"
        setupDestinationsList(categoria)
    }

    private fun setupDestinationsList(categoria: String) {
        val destinos = arrayListOf<String>()
        val destinosCompletos = arrayListOf<JSONObject>()
        val json = JSONObject(loadJSON.loadJSONFromAsset(this, "destinos.json"))
        val destinosJson = json.getJSONArray("destinos_turisticos")

        for (i in 0 until destinosJson.length()) {
            val destino = destinosJson.getJSONObject(i)
            if (categoria == "Todos" || destino.getString("categoria") == categoria) {
                destinos.add(destino.getString("nombre"))
                destinosCompletos.add(destino)
            }
        }

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, destinos)
        binding.listaDestinos.adapter = adapter
        setupListClickListener(destinosCompletos)
    }

    private fun setupListClickListener(destinosCompletos: ArrayList<JSONObject>) {
        binding.listaDestinos.setOnItemClickListener { _, _, position, _ ->
            val destinoSeleccionado = destinosCompletos[position]
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("destino", destinoSeleccionado.toString())
            startActivity(intent)
        }
    }
}