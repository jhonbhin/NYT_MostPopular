package com.example.nyt_mostpopular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nyt_mostpopular.databinding.DetailsArticulosBinding
import com.squareup.picasso.Picasso

class ArticuloAdapter (val articulos: List<NYTArticle>, val clickListener: (NYTArticle) -> Unit) :

    RecyclerView.Adapter<ArticuloAdapter.ArticuloViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticuloViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ArticuloViewHolder(layoutInflater.inflate(R.layout.details_articulos, parent, false))
    }

    override fun onBindViewHolder(holder: ArticuloViewHolder, position: Int) {
        holder.render(articulos[position])

        holder.itemViewNYTArticulo.setOnClickListener {
            clickListener(articulos[position])
        }
    }

    override fun getItemCount(): Int {
        return articulos.size
    }

    class ArticuloViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val binding = DetailsArticulosBinding.bind(view)
        val itemViewNYTArticulo = binding.itemViewNYTArticulo

        fun render(nytArticle: NYTArticle) {
            with(binding) {
                textViewTitulo.text = nytArticle.title
                textViewFecha.text = nytArticle.published_date
                Picasso.get().load(nytArticle.media[0].mediaMetadata.last().url)
                    .into(binding.imageViewArticulo)
            }
        }

    }

}


