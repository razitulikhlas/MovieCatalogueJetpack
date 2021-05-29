package com.razit.moviecatalogue.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.razit.moviecatalogue.R
import com.razit.moviecatalogue.adapter.FilmAdapter
import com.razit.moviecatalogue.adapter.MoviesCallback
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.databinding.FragmentFilmBinding
import com.razit.moviecatalogue.detail.DetailFilm
import com.razit.moviecatalogue.utils.Constanta.DATA_EXIST
import com.razit.moviecatalogue.utils.Constanta.DELAY_GET_DATA
import com.razit.moviecatalogue.utils.Constanta.isNetworkAvailable
import com.razit.moviecatalogue.viewmodel.FilmViewModel
import com.razit.moviecatalogue.viewmodel.MainState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmFragment : Fragment(), MoviesCallback {

    private lateinit var fragmentFilmBinding: FragmentFilmBinding
    private val viewModel: FilmViewModel by viewModel()
    private lateinit var moviesAdapter: FilmAdapter


    companion object {

        const val CATEGORY = "category"
        const val MOVIES = "movies"
        const val TVS_SHOW = "tvShow"


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            moviesAdapter = FilmAdapter()
            observers()
            getData()
            fragmentFilmBinding.frameError.btnReload.setOnClickListener {
                fragmentFilmBinding.frameError.root.visibility = View.GONE
                fragmentFilmBinding.progressbar.visibility = View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    getData()
                }, DELAY_GET_DATA)

            }
            with(fragmentFilmBinding.rcvFilm) {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
        }
    }

    private fun getData() {
        val category = arguments?.getString(CATEGORY)
        if (isNetworkAvailable(requireActivity())) {
            if (category == MOVIES) {
                viewModel.getMovies()
            } else {
                viewModel.getTvShow()
            }
        } else {
            if (!DATA_EXIST) {
                fragmentFilmBinding.frameError.root.visibility = View.VISIBLE
                fragmentFilmBinding.progressbar.visibility = View.GONE
            }
        }
    }

    private fun observers() {
        viewModel.film.observe(viewLifecycleOwner, { handleResponseMovies(it) })
        viewModel.state.observe(viewLifecycleOwner, { handleStateUi(it) })
    }

    private fun handleStateUi(state: MainState?) {
        when (state) {
            is MainState.Loading -> isLoading(state.isLoading)
            is MainState.Message -> Toast.makeText(activity, state.message, Toast.LENGTH_SHORT)
                .show()
            null -> return
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            fragmentFilmBinding.progressbar.visibility = View.VISIBLE
        } else {
            fragmentFilmBinding.progressbar.visibility = View.GONE
        }
    }

    private fun handleResponseMovies(it: List<FilmEntity>) {
        if (it.isNotEmpty()) {
            DATA_EXIST = true
        }
        val context = fragmentFilmBinding.rcvFilm.context
        val layoutAnimator = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation)
        with(fragmentFilmBinding.rcvFilm) {
            layoutAnimation = layoutAnimator
            adapter?.notifyDataSetChanged()
            scheduleLayoutAnimation()
        }
        moviesAdapter.setMovies(it, this, context)
    }

    override fun onClick(movies: FilmEntity) {
        val intent = Intent(activity, DetailFilm::class.java)
        intent.putExtra(DetailFilm.DATA, movies)
        startActivity(intent)
    }
}