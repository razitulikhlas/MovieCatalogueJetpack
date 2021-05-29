package com.razit.moviecatalogue.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.R
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.databinding.ActivityDetailFilmBinding
import com.razit.moviecatalogue.utils.Constanta.DELAY_GET_DATA
import com.razit.moviecatalogue.utils.Constanta.DETAIL_EXIST
import com.razit.moviecatalogue.utils.Constanta.isNetworkAvailable
import com.razit.moviecatalogue.viewmodel.FilmViewModel
import com.razit.moviecatalogue.viewmodel.MainState
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFilm : AppCompatActivity() {

    companion object {
        const val DATA = "datafilm"
    }

    private val viewModel: FilmViewModel by viewModel()
    private lateinit var activityDetailFilm: ActivityDetailFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailFilm = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(activityDetailFilm.root)
        supportActionBar?.hide()
        observer()
        loadData()
        setUpUI()
    }

    private fun setUpUI() {
        with(activityDetailFilm) {
            frameError.btnReload.setOnClickListener {
                activityDetailFilm.frameError.root.visibility = View.GONE
                activityDetailFilm.progressbar.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    loadData()
                }, DELAY_GET_DATA)
            }
            ivBack.setOnClickListener {
                DETAIL_EXIST = false
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        DETAIL_EXIST = false
    }


    private fun observer() {
        viewModel.detailFilm.observe(this, { handleData(it) })
        viewModel.state.observe(this, { handleStateUI(it) })
    }

    private fun loadData() {
        if (isNetworkAvailable(this)) {
            getData()
        } else {
            if (!DETAIL_EXIST) {
                activityDetailFilm.progressbar.visibility = View.GONE
                activityDetailFilm.frameError.root.visibility = View.VISIBLE
            }
        }
    }

    private fun getData() {
        val item = intent.getParcelableExtra<FilmEntity>(DATA)
        if (item?.type == BuildConfig.MOVIES) {
            item.id?.let {
                viewModel.getDetailMovies(it)
            }
        } else {
            item?.id?.let {
                viewModel.getDetailTvShow(it)
            }
        }
    }

    private fun handleStateUI(state: MainState?) {
        when (state) {
            is MainState.Loading -> isLoading(state.isLoading)
            else -> return
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            activityDetailFilm.progressbar.visibility = View.VISIBLE
        } else {
            activityDetailFilm.progressbar.visibility = View.GONE

        }
    }

    private fun handleData(film: FilmEntity?) {
        with(activityDetailFilm) {
            if (film != null) {
                DETAIL_EXIST = true
                containerData.visibility = View.VISIBLE
            }
            Glide.with(this@DetailFilm)
                .load(BuildConfig.IMAGES_URL + film?.imageUrl)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgPoster)
            film?.let { it ->
                tvGenre.text = it.genre
                tvDescription.text = it.description
                tvTitle.text = it.title
                tvLanguage.text = it.language
                tvDetailRating.text = it.rating.toString()
            }

        }
    }


}