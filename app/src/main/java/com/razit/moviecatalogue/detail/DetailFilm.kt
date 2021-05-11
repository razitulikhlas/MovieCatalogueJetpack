package com.razit.moviecatalogue.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.razit.moviecatalogue.R
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.razit.moviecatalogue.fragment.FilmFragment
import com.razit.moviecatalogue.viewmodel.FilmViewModel

class DetailFilm : AppCompatActivity() {

    companion object{
        const val ID = "idFilm"
        const val CATEGORY = "category"
    }
    private lateinit var film : FilmEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailFilm = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(activityDetailFilm.root)
        supportActionBar?.title = "Detail Film"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val viewModel =  ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FilmViewModel::class.java]


        val extra = intent.extras

        if(extra != null){
            val id = extra.getInt(ID)
            val category = extra.getString(CATEGORY)
            film = if(category == FilmFragment.MOVIES){
                viewModel.getDetailMovies(id)
            }else{
                viewModel.getDetailTvShow(id)
            }

            with(activityDetailFilm){
                Glide.with(this@DetailFilm)
                    .load(film.imageUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)
                tvGenre.text = film.genre
                tvDesc.text=film.description
                tvTitle.text = film.title
                tvDate.text = film.release
                tvDirector.text = film.director
            }

        }


    }
}