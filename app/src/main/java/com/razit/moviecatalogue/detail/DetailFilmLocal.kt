package com.razit.moviecatalogue.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.R
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.databinding.ActivityDetailFilmLocalBinding
import com.razit.moviecatalogue.viewmodel.FilmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFilmLocal : AppCompatActivity() {

    companion object {
        const val DATA = "datafilm"
    }

    private val viewModel: FilmViewModel by viewModel()
    private var exist: Boolean? = null
    private lateinit var activityDetailFilm: ActivityDetailFilmLocalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailFilm = ActivityDetailFilmLocalBinding.inflate(layoutInflater)
        setContentView(activityDetailFilm.root)

        val item = intent.getParcelableExtra<FilmEntity>(DATA)

        checkData(item?.id!!)
        setUpUI(item)
    }

    private fun setUpUI(filmEntity:FilmEntity) {
        with(activityDetailFilm) {
            activityDetailFilm.progressbarDetail.visibility = View.GONE
            Glide.with(this@DetailFilmLocal)
                .load(BuildConfig.IMAGES_URL + filmEntity.imageUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgPoster)
            filmEntity.let { it ->
                tvGenre.text = it.genre
                tvDescription.text = it.description
                tvTitle.text = it.title
                tvLanguage.text = it.language
                tvDetailRating.text = it.rating.toString()
            }
            imgFavorite.setOnClickListener {
                if (exist!!) {
                    val status = viewModel.delete(filmEntity)
                    if (status > 0) {
                        activityDetailFilm.imgFavorite.setImageResource(R.drawable.ic_favorite)
                        exist = !exist!!
                        Toast.makeText(this@DetailFilmLocal, "Success delete data", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this@DetailFilmLocal, "Failed delete data", Toast.LENGTH_LONG)
                            .show()
                    }

                } else {
                    val status = viewModel.saveToFavorite(filmEntity)
                    if (status > 0) {
                        activityDetailFilm.imgFavorite.setImageResource(R.drawable.ic_favorite_active)
                        exist = !exist!!
                        Toast.makeText(this@DetailFilmLocal, "Success save data", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this@DetailFilmLocal, "Failed save data", Toast.LENGTH_LONG)
                            .show()
                    }

                }

            }

        }
    }


    private fun checkData(id: Int) {
        viewModel.checkData(id)
        viewModel.checkData.observe(this, {
            if (it) {
                exist = it
                activityDetailFilm.imgFavorite.setImageResource(R.drawable.ic_favorite_active)
                Log.e("TAG", "observer: ada")
            } else {
                exist = it
                activityDetailFilm.imgFavorite.setImageResource(R.drawable.ic_favorite)
                Log.e("TAG", "observer: tidak ada")
            }
        })
    }
}