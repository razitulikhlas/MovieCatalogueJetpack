package com.razit.moviecatalogue.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.R
import com.razit.moviecatalogue.adapter.MoviesCallback
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.databinding.ItemFilmBinding

class MoviesPagingAdapter() :
    PagingDataAdapter<FilmEntity, MoviesPagingAdapter.MoviesViewHolder>(MoviesUtil) {


    object MoviesUtil : DiffUtil.ItemCallback<FilmEntity>() {
        override fun areItemsTheSame(oldItem: FilmEntity, newItem: FilmEntity): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilmEntity, newItem: FilmEntity): Boolean {
            return oldItem == newItem
        }
    }

    private lateinit var moviesCallback: MoviesCallback

    fun setCallBack(moviesCallback: MoviesCallback) {
        this.moviesCallback = moviesCallback
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

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        holder.bind(item!!, moviesCallback)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsFilm = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsFilm)
    }
}