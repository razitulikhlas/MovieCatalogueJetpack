package com.razit.moviecatalogue.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.razit.moviecatalogue.adapter.FilmAdapter

import com.razit.moviecatalogue.databinding.FragmentFilmBinding
import com.razit.moviecatalogue.viewmodel.FilmViewModel

class FilmFragment : Fragment() {

    private lateinit var fragmentFilmBinding: FragmentFilmBinding

    companion object{

        const val CATEGORY = "category"
        const val MOVIES = "movies"
        const val TVS_SHOW = "tvShow"

        fun newInstance(category:String): FilmFragment{
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
        if(activity != null){
            val viewModel =  ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FilmViewModel::class.java]
            val category = arguments?.getString(CATEGORY)
            val moviesAdapter = FilmAdapter()
            if(category == MOVIES){
                moviesAdapter.setMovies(viewModel.getMovies(), MOVIES)
            }else{
                moviesAdapter.setMovies(viewModel.getTvShow(), TVS_SHOW)
            }



            with(fragmentFilmBinding.rcvFilm) {
                layoutManager = GridLayoutManager(context,2)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
        }
    }
}