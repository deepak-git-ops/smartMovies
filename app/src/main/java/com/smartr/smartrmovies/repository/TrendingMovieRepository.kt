package com.smartr.smartrmovies.repository

import android.util.Log
import com.smartr.smartrmovies.model.SearchMovies
import com.smartr.smartrmovies.model.TrendingData
import com.smartr.smartrmovies.networkUtils.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class TrendingMovieRepository @Inject constructor(private val apiInterface: ApiInterface) {
    val TAG = "Movie repository :"

    fun fetchData(): Flow<TrendingData> = flow {
        try {
            val response = apiInterface.getTrending()
            Log.d(TAG,response.toString())
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)


    fun fetchSearchData(query: String): Flow<SearchMovies> = flow{
        try {
            val response = apiInterface.searchMovies(query)
            Log.d(TAG,response.toString())
            emit(response)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)


 fun fetchSimilarData(id: String) : Flow<TrendingData> = flow {
    try {
        val response = apiInterface.showSimilarMovies(id)
        Log.d(TAG,response.toString())
        emit(response)
    } catch (e : Exception) {
        e.printStackTrace()
    }
   }.flowOn(Dispatchers.IO)
 }