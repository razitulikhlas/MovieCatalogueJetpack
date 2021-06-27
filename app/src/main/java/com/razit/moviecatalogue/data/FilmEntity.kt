package com.razit.moviecatalogue.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tbl_movies")
@Parcelize
data class FilmEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int ? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "image_url")
    var imageUrl: String? = null,
    @ColumnInfo(name = "release_date")
    var release: String? = null,
    @ColumnInfo(name = "language")
    var language: String? = null,
    @ColumnInfo(name = "rating")
    var rating:Double?=null,
    @ColumnInfo(name = "type")
    var type:String? = null,
    @ColumnInfo(name = "genre")
    var genre:String? = null
): Parcelable