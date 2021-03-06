package com.razit.moviecatalogue.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.adapter.MoviesCallback
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.databinding.FragmentFilmBinding
import com.razit.moviecatalogue.detail.DetailFilm
import com.razit.moviecatalogue.paging.MoviesPagingAdapter
import com.razit.moviecatalogue.viewmodel.FilmViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmFragment : Fragment(), MoviesCallback {

    private lateinit var fragmentFilmBinding: FragmentFilmBinding
    private val viewModelMovies: FilmViewModel by viewModel()
    private lateinit var moviesPagingAdapter: MoviesPagingAdapter


    companion object {

        const val CATEGORY = "category"

        fun newInstance(category: String): FilmFragment {
            val args = Bundle()
            args.putSerializable(CATEGORY, category)
            val fragment = FilmFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFilmBinding = FragmentFilmBinding.inflate(layoutInflater, container, false)
        return fragmentFilmBinding.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            moviesPagingAdapter = MoviesPagingAdapter()
            moviesPagingAdapter.setCallBack(this)
            createFragment()
        }
    }

    @ExperimentalPagingApi
    private fun createFragment() {
        val category = arguments?.getString(CATEGORY)
        if (category == BuildConfig.MOVIES) {
            getMovies()
        } else {
            getTvShow()
        }
    }

    private fun getTvShow() {
        lifecycleScope.launch {
            viewModelMovies.flowTvSHow.collectLatest {
                moviesPagingAdapter.refresh()
                moviesPagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                with(fragmentFilmBinding.rcvFilm) {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = moviesPagingAdapter
                }
            }
        }
    }

    private fun getMovies() {
        lifecycleScope.launch {
            viewModelMovies.flowMovies.collectLatest {
                moviesPagingAdapter.refresh()
                moviesPagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                with(fragmentFilmBinding.rcvFilm) {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = moviesPagingAdapter
                }
            }
        }
    }


    override fun onClick(movies: FilmEntity) {
        val intent = Intent(activity, DetailFilm::class.java)
        intent.putExtra(DetailFilm.DATA, movies)
        startActivity(intent)
    }
}