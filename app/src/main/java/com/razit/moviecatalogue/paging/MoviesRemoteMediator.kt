package com.razit.moviecatalogue.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.RemoteKey
import com.razit.moviecatalogue.data.local.MoviesDao
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.FilmService
import java.io.InvalidObjectException

@ExperimentalPagingApi
class MoviesRemoteMediator(private val networkMapperMovies: NetworkMapperMovies,private val dao: MoviesDao,private val filmService: FilmService,private val initialPage:Int):RemoteMediator<Int,FilmEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmEntity>
    ): MediatorResult {
        return try {
            val page=when(loadType){
                LoadType.APPEND->{
                    val remoteKey = getLastKey(state)?:throw InvalidObjectException("Error")
                    remoteKey.nexKey?:return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.PREPEND->{
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH->{
                    val remoteKey = getCloseKey(state)
                    remoteKey?.nexKey?.minus(1)?:initialPage
                }
            }

            val response = filmService.getMoviesPages(page = page)
            val endOfPagination = response.body()?.results?.size!! < state.config.pageSize
            if(response.isSuccessful){
                response.body()?.let {
                    if(loadType == LoadType.REFRESH){
                        dao.deleteFilm()
                        dao.deleteRemoteKey()
                    }
                    val prev = if(page==initialPage)null else page-1
                    val next = if(endOfPagination)null else page+1
                   val list = response.body()?.results?.map {
                        RemoteKey(it.title!!,next!!,prev!!)
                    }
                    if(list!=null){
                        dao.insertRemoteKey(list)
                    }
                    val film = networkMapperMovies.fromMoviesToFilmEntity(it.results!!)
                    dao.insertAll(film)
                }
                MediatorResult.Success(endOfPagination)
            }else{
                MediatorResult.Success(endOfPaginationReached = true)
            }

        }catch (e:Exception){
            MediatorResult.Error(e)
        }
    }

    suspend fun getCloseKey(state:PagingState<Int,FilmEntity>):RemoteKey?{
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let {item->
                item.title?.let{title->
                    dao.getRemoteKey(title)
                }
            }
        }
    }

    suspend fun getLastKey(state: PagingState<Int, FilmEntity>):RemoteKey?{
        return  state.lastItemOrNull()?.let {item->
            item.title?.let { title->
                dao.getRemoteKey(title)
            }

        }
    }

}