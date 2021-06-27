package com.razit.moviecatalogue.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.Resource
import com.razit.moviecatalogue.repository.RepositoryMoviesImpl
import com.razit.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.launch

class FilmViewModel(
    private val repositoryMovies: RepositoryMoviesImpl,
    private val networkMapperMovies: NetworkMapperMovies,
) : ViewModel() {
    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> = _state

    private var _checkData = MutableLiveData<Boolean>()
    var checkData: LiveData<Boolean> = _checkData

    private val _film = MutableLiveData<List<FilmEntity>>()
    val film: LiveData<List<FilmEntity>> = _film

    private val _detailFilm = MutableLiveData<FilmEntity>()
    val detailFilm: LiveData<FilmEntity> = _detailFilm


    private fun setIsLoading(b: Boolean) {
        _state.value = MainState.Loading(b)
    }

    private fun setFailedGetData(data: Boolean) {
        _state.value = MainState.FailedGetData(data)
    }

    val flowMovies = repositoryMovies.getMoviesPage().cachedIn(viewModelScope)
    val flowTvSHow = repositoryMovies.getTvShowPage().cachedIn(viewModelScope)
    val flowLocalMovies = repositoryMovies.getMoviesFromLocal(BuildConfig.MOVIES)
    val flowLocalTvShow = repositoryMovies.getMoviesFromLocal(BuildConfig.TVSHOW)



    fun saveToFavorite(filmEntity: FilmEntity):Long {
        return repositoryMovies.saveToFavorite(filmEntity)
    }

    fun deleteFavorite(id: Int, type: String) {
        repositoryMovies.deleteFilmFavorite(id, type)
    }

    fun delete(filmEntity: FilmEntity) :Int {
        return repositoryMovies.delete(filmEntity)
    }



    fun checkData(id:Int){
        viewModelScope.launch {
            val status = repositoryMovies.isSave(id)
            _checkData.postValue(status)
        }

    }


    fun getDetailTvShow(id: Int) {
        EspressoIdlingResource.increment()
        setFailedGetData(false)
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
                    setFailedGetData(true)
                }
            }
        }
    }

    fun getDetailMovies(id: Int) {
        EspressoIdlingResource.increment()
        setFailedGetData(false)
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
                    setFailedGetData(true)
                }
            }
        }
    }

}

sealed class MainState {
    data class Loading(val isLoading: Boolean) : MainState()
    data class FailedGetData(val data: Boolean) : MainState()
    data class SuccessDelete(val data:Boolean):MainState()
}