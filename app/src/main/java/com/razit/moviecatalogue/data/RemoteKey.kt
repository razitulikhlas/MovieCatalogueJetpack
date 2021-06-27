package com.razit.moviecatalogue.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_remoteKey")
data class RemoteKey (
    @PrimaryKey(autoGenerate = false)
    val id :String,
    val nexKey:Int,
    val prevKey:Int
)