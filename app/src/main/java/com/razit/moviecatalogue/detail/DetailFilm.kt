package com.razit.moviecatalogue.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
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
        const val TYPE = "ONLINE/OFFLINE"
        const val LOCAL = "LOCAL"
    }

    private val viewModel: FilmViewModel by viewModel()
    private var exist: Boolean? = null
    private lateinit var activityDetailFilm: ActivityDetailFilmBinding
    private var item = FilmEntity()
    private var filmEntity = FilmEntity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailFilm = ActivityDetailFilmBinding.inflate(layoutInflater)

        setContentView(activityDetailFilm.root)
        supportActionBar?.hide()
        item = intent.getParcelableExtra(DATA)!!
        observer()
        loadData()
        checkData()
        setUpUI()
    }

    private fun checkData() {
        viewModel.checkData(item.id!!)
    }

    private fun setUpUI() {
        with(activityDetailFilm) {
            frameError.btnReload.setOnClickListener {
                activityDetailFilm.frameError.root.visibility = View.GONE
                activityDetailFilm.progressbarDetail.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    loadData()
                }, DELAY_GET_DATA)
            }
            imgFavorite.setOnClickListener {
                if (exist!!) {
                    val status = viewModel.delete(item)
                    if(status > 0){
                        activityDetailFilm.imgFavorite.setImageResource(R.drawable.ic_favorite)
                        exist=!exist!!
                        Toast.makeText(this@DetailFilm,"Success delete data",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@DetailFilm,"Failed delete data",Toast.LENGTH_LONG).show()
                    }

                } else {
                    val status = viewModel.saveToFavorite(filmEntity)
                    if(status> 0){
                        activityDetailFilm.imgFavorite.setImageResource(R.drawable.ic_favorite_active)
                        exist=!exist!!
                        Toast.makeText(this@DetailFilm,"Success save data",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@DetailFilm,"Failed save data",Toast.LENGTH_LONG).show()
                    }

                }

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

    private fun loadData() {
        val type = intent.getStringExtra(TYPE)
        if (type == LOCAL) {
            Log.e("Razit", "loadData: local")
            with(activityDetailFilm) {
                activityDetailFilm.progressbarDetail.visibility = View.GONE
                Glide.with(this@DetailFilm)
                    .load(BuildConfig.IMAGES_URL + item.imageUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
                item.let { it ->
                    tvGenre.text = it.genre
                    tvDescription.text = it.description
                    tvTitle.text = it.title
                    tvLanguage.text = it.language
                    tvDetailRating.text = it.rating.toString()
                    it.type = item.type
                    filmEntity = it
                }
            }
        } else {
            if (isNetworkAvailable(this)) {
                getData()
            } else {
                if (!DETAIL_EXIST) {
                    activityDetailFilm.progressbarDetail.visibility = View.GONE
                    activityDetailFilm.frameError.root.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun getData() {

        if (item.type == BuildConfig.MOVIES) {
            item.id?.let {
                viewModel.getDetailMovies(it)
            }
        } else {
            item.id?.let {
                viewModel.getDetailTvShow(it)
            }
        }
    }

    private fun handleStateUI(state: MainState?) {
        when (state) {
            is MainState.Loading -> isLoading(state.isLoading)
            is MainState.FailedGetData -> failedGetData(state.data)
            else -> return
        }
    }

    private fun failedGetData(data: Boolean) {
        if (data) {
            activityDetailFilm.progressbarDetail.visibility = View.GONE
            activityDetailFilm.frameError.root.visibility = View.VISIBLE
        } else {
            activityDetailFilm.frameError.root.visibility = View.GONE
            activityDetailFilm.progressbarDetail.visibility = View.VISIBLE
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            activityDetailFilm.progressbarDetail.visibility = View.VISIBLE
        } else {
            activityDetailFilm.progressbarDetail.visibility = View.GONE

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
                it.type = item.type
                filmEntity = it
            }
        }
    }

}