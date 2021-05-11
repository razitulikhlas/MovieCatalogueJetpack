package com.razit.moviecatalogue.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.razit.moviecatalogue.R
import com.razit.moviecatalogue.data.FilmEntity

import com.razit.moviecatalogue.databinding.ItemFilmBinding
import com.razit.moviecatalogue.detail.DetailFilm

class FilmAdapter: RecyclerView.Adapter<FilmAdapter.MoviesViewHolder>() {
    private var listFilm = ArrayList<FilmEntity>()
    private var category = ""

    fun setMovies(film : List<FilmEntity>?,category :String){
        if (film== null) return
        this.listFilm.clear()
        this.listFilm.addAll(film)
        this.category = category
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsFilm = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsFilm)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = listFilm[position]
        holder.bind(movies,category)
    }

    override fun getItemCount(): Int = listFilm.size


    class MoviesViewHolder(private val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(film: FilmEntity,category: String) {
            with(binding) {
                tvTitle.text = film.title
                tvDate.text = film.release
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilm::class.java)
                    intent.putExtra(DetailFilm.ID, film.id)
                    intent.putExtra(DetailFilm.CATEGORY,category)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(film.imageUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }
    }
}