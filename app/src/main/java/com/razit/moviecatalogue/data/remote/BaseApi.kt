package com.razit.moviecatalogue.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseApi {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T) : Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            }catch (t: Throwable) {
                when(t) {
                    is HttpException -> {
                        Resource.Failure(false, t.code(), t.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }
}