package com.razit.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.Resource
import com.razit.moviecatalogue.repository.RepositoryMoviesImpl
import com.razit.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.launch

class FilmViewModel(
    private val repositoryMovies: RepositoryMoviesImpl,
    private val networkMapperMovies: NetworkMapperMovies
) : ViewModel() {
    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> = _state

    private val _film = MutableLiveData<List<FilmEntity>>()
    val film: LiveData<List<FilmEntity>> = _film

    private val _detailFilm = MutableLiveData<FilmEntity>()
    val detailFilm: LiveData<FilmEntity> = _detailFilm


    private fun setIsLoading(b: Boolean) {
        _state.value = MainState.Loading(b)
    }

    private fun setMessage(message: String) {
        _state.value = MainState.Message(message)
    }


    fun getMovies() {
        setIsLoading(true)
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            when (val response = repositoryMovies.getMovies()) {
                is Resource.Success -> {
                    EspressoIdlingResource.decrement()
                    setIsLoading(false)
                    val mapMoviesToFilmEntity = response.value.results?.let {
                        networkMapperMovies.fromMoviesToFilmEntity(it)
                    }
                    mapMoviesToFilmEntity.let { _film.value = it }
                }
                is Resource.Failure -> {
                    EspressoIdlingResource.decrement()
                    setIsLoading(false)
                    when {
                        response.isNetworkError -> {
                        }
                        else -> {
                            val message = response.errorBody.toString()
                            setMessage(message)
                        }
                    }

                }
            }
        }

    }

    fun getTvShow() {
        EspressoIdlingResource.increment()
        setIsLoading(true)
        viewModelScope.launch {
            when (val response = repositoryMovies.getTvShow()) {
                is Resource.Success -> {
                    EspressoIdlingResource.decrement()
                    setIsLoading(false)
                    val mapTvShowToFilmEntity = response.value.results?.let {
                        networkMapperMovies.fromTvShowToFilmEntity(it)
                    }
                    mapTvShowToFilmEntity.let { _film.value = it }
                }
                is Resource.Failure -> {
                    EspressoIdlingResource.decrement()
                    setIsLoading(false)
                    when {
                        response.isNetworkError -> {
                        }
                        else -> {
                            val message = response.errorBody.toString()
                            setMessage(message)
                        }
                    }
                }
            }
        }


    }


    fun getDetailTvShow(id: Int) {
        EspressoIdlingResource.increment()
        setIsLoading(true)
        viewModelScope.launch {
            when (val response = repositoryMovies.getDetailTvShow(id)) {
                is Resource.Success -> {
                    EspressoIdlingResource.decrement()
                    setIsLoading(false)
                    val mapDetailTvShowToFilmEntity = response.value.let {
                        networkMapperMovies.detailTvShowToFilmEntity(it)
                    }
                    mapDetailTvShowToFilmEntity.let { _detailFilm.value = it }
                }
                is Resource.Failure -> {
                    EspressoIdlingResource.decrement()
                    setIsLoading(false)
                    when {
                        response.isNetworkError -> {
                        }
                        else -> {
                            val message = response.errorBody.toString()
                            setMessage(message)
                        }
                    }

                }
            }
        }
    }

    fun getDetailMovies(id: Int) {
        EspressoIdlingResource.increment()
        setIsLoading(true)
        viewModelScope.launch {
            when (val response = repositoryMovies.getDetailMovies(id)) {
                is Resource.Success -> {
                    EspressoIdlingResource.decrement()
                    setIsLoading(false)
                    val mapDetailMoviesToFilmEntity = response.value.let {
                        networkMapperMovies.fromDetailMoviesToFilmEntity(it)
                    }
                    mapDetailMoviesToFilmEntity.let {
                        _detailFilm.value = it
                    }
                }
                is Resource.Failure -> {
                    EspressoIdlingResource.decrement()
                    setIsLoading(false)
                    when {
                        response.isNetworkError -> {
                        }
                        else -> {
                            val message = response.errorBody.toString()
                            setMessage(message)
                        }
                    }

                }
            }
        }
    }

}

sealed class MainState {
    data class Loading(val isLoading: Boolean) : MainState()
    data class Message(val message: String) : MainState()

}