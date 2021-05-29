package com.razit.moviecatalogue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.R
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.databinding.ItemFilmBinding


class FilmAdapter : RecyclerView.Adapter<FilmAdapter.MoviesViewHolder>() {
    private var listFilm = ArrayList<FilmEntity>()
    private lateinit var moviesCallback: MoviesCallback
    private lateinit var mContext: Context

    fun setMovies(film: List<FilmEntity>?, moviesCallback: MoviesCallback, mContext: Context) {
        if (film == null) return
        this.listFilm.clear()
        this.listFilm.addAll(film)
        this.moviesCallback = moviesCallback
        this.mContext = mContext
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsFilm = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsFilm)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = listFilm[position]
        holder.bind(movies, moviesCallback)
        val animation: Animation = AnimationUtils.loadAnimation(
            mContext, R.anim.slide_in
        )
        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int = listFilm.size

    override fun onViewDetachedFromWindow(holder: MoviesViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }


    class MoviesViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmEntity, moviesCallback: MoviesCallback) {
            with(binding) {
                tvTitle.text = film.title
                tvDate.text = film.release
                tvRating.text = film.rating.toString()
                itemView.setOnClickListener {
                    moviesCallback.onClick(film)
                }
                Glide.with(itemView.context)
                    .load(BuildConfig.IMAGES_URL + film.imageUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }
}