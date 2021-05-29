package com.razit.moviecatalogue.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmEntity(
    var id: Int ? = null,
    var title: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var release: String? = null,
    var language: String? = null,
    var rating:Double?=null,
    var type:String? = null,
    var genre:String? = null
): Parcelable