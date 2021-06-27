package com.razit.moviecatalogue.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.adapter.MoviesCallback
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.databinding.FragmentLocalBinding
import com.razit.moviecatalogue.detail.DetailFilm
import com.razit.moviecatalogue.detail.DetailFilmLocal
import com.razit.moviecatalogue.paging.MoviesPagingAdapter
import com.razit.moviecatalogue.viewmodel.FilmViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocalFragment : Fragment(), MoviesCallback {
    private val viewModelMovies: FilmViewModel by viewModel()

    private lateinit var fragmentLocalBinding: FragmentLocalBinding
    private lateinit var moviesPagingAdapter: MoviesPagingAdapter

    companion object {
        const val CATEGORY = "category"

        fun newInstance(category: String): LocalFragment {
            val args = Bundle()
            args.putSerializable(CATEGORY, category)
            val fragment = LocalFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLocalBinding = FragmentLocalBinding.inflate(layoutInflater, container, false)
        return fragmentLocalBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            moviesPagingAdapter = MoviesPagingAdapter()
            moviesPagingAdapter.setCallBack(this)
            createFragment()
        }
    }

    private fun createFragment() {
        val category = arguments?.getString(FilmFragment.CATEGORY)
        if (category == BuildConfig.MOVIES) {
            getMovies()
        } else {
            getTvShow()
        }
    }

    private fun getMovies() {
        lifecycleScope.launch {
            viewModelMovies.flowLocalMovies.observe(viewLifecycleOwner, {
                moviesPagingAdapter.refresh()
                moviesPagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                with(fragmentLocalBinding.rcvFilm) {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = moviesPagingAdapter
                }
            })
        }
    }

    private fun getTvShow() {
        lifecycleScope.launch {
            viewModelMovies.flowLocalTvShow.observe(viewLifecycleOwner, {
                moviesPagingAdapter.refresh()
                moviesPagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                with(fragmentLocalBinding.rcvFilm) {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = moviesPagingAdapter
                }
            })
        }
    }

    override fun onClick(movies: FilmEntity) {
        val intent = Intent(activity, DetailFilmLocal::class.java)
        intent.putExtra(DetailFilm.DATA, movies)
        intent.putExtra(DetailFilm.TYPE, DetailFilm.LOCAL)
        startActivity(intent)
    }

}