package com.razit.moviecatalogue.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.RemoteKey

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies:List<FilmEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(filmEntity: FilmEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemoteKey(remoteKey:List<RemoteKey>)

    @Query("SELECT * FROM tbl_remoteKey WHERE id = :id")
    suspend fun getRemoteKey(id:String):RemoteKey

    @Query("SELECT EXISTS(SELECT * FROM TBL_MOVIES WHERE id = :id)")
    suspend fun isSave(id : Int) : Boolean

    @Query("SELECT * FROM TBL_MOVIES")
    fun getAllMovies() : PagingSource<Int,FilmEntity>

    @Query("SELECT * FROM TBL_MOVIES  WHERE type = :type")
    fun getAllFilmByType(type: String) : PagingSource<Int,FilmEntity>


    @Query("DELETE FROM TBL_MOVIES WHERE id = :id AND type = :type")
    fun deleteByIdAndType(id:Int,type:String)

    @Delete
    fun delete(movies: FilmEntity) : Int

    @Query("DELETE FROM tbl_movies")
    suspend fun deleteFilm()

    @Query("DELETE FROM tbl_remoteKey")
    suspend fun deleteRemoteKey()

}