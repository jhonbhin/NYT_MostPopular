package com.example.nyt_mostpopular

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nyt_mostpopular.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArticuloAdapter

    private var articlesList = mutableListOf<NYTArticle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArticuloAdapter(articlesList) {
            binding.webViewArticulos.loadUrl(it.url)
            //Toast.makeText(this, it.url, Toast.LENGTH_LONG).show()
        }

        binding.recyclerViewArticulos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewArticulos.adapter = adapter

        getEmailPopular()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getEmailPopular() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<NYTJsonResponse> =
                getRetrofit().create(APIService::class.java)
                    .getMostPopularArticles("emailed/7.json?api-key=9DnPkV6meO5MHMwNIE6Qs2M9tJilxpOU")
            val nytJsonResponse: NYTJsonResponse? = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    // Respuesta exitosa, mostrar
                    if (nytJsonResponse != null) {
                        articlesList.clear()
                        articlesList.addAll(nytJsonResponse.results)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    // Mostrar un error
                    Toast.makeText(this@MainActivity, "Ocurrió un error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}