package com.example.taller1compumovil.LogicaActivities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1compumovil.databinding.ActivityDetailBinding
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    // 🔑 Mapa de destinos → ciudades reconocidas por OpenWeather
    private val mapaCiudades = mapOf(
        "Baia do Sancho" to "Fernando de Noronha",
        "Eagle Beach" to "Oranjestad",
        "El Nido" to "El Nido",
        "Monte Everest" to "Kathmandu",
        "Mont Blanc" to "Chamonix",
        "Aconcagua" to "Mendoza",
        "Machu Picchu" to "Cusco",
        "Roma" to "Rome",
        "Petra" to "Wadi Musa",
        "Gran Muralla China" to "Beijing",
        "Taj Mahal" to "Agra",
        "Pirámides de Giza" to "Giza",
        "Amazonas" to "Manaus",
        "Congo" to "Kinshasa",
        "Borneo" to "Kuching"
    )

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

                // 🔎 Buscar ciudad válida en el mapa, si no existe usar el nombre tal cual
                val destinoNombre = destino.getString("nombre")
                val ciudad = mapaCiudades[destinoNombre] ?: destinoNombre
                getWeather(ciudad)
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
                if (!yaExiste) {
                    MainActivity.favoritos.add(destino)
                    Toast.makeText(this, "Destino añadido a favoritos", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Ya lo tenías en favoritos", Toast.LENGTH_SHORT).show()
                }
                binding.btnAddFavorite.isEnabled = false
            } catch (e: Exception) {
                Toast.makeText(this, "Error al añadir a favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeather(ciudad: String) {
        val apiKey = "8bcf9aad58c4635a81e9d0048550425f" // tu API Key
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$ciudad&appid=$apiKey&units=metric&lang=es"


        println("🌍 URL clima: $url")

        Thread {
            try {
                val client = okhttp3.OkHttpClient()
                val request = okhttp3.Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val body = response.body?.string()

                body?.let {
                    val json = JSONObject(it)
                    val temp = json.getJSONObject("main").getDouble("temp")
                    val descripcion = json.getJSONArray("weather")
                        .getJSONObject(0).getString("description")

                    runOnUiThread {
                        binding.txtClima.text = "Clima: $descripcion, ${temp}°C"
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    binding.txtClima.text = "Clima: NA"
                }
            }
        }.start()
    }

}
