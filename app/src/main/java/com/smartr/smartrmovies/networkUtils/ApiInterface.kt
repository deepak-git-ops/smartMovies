package com.smartr.smartrmovies.networkUtils

import com.smartr.smartrmovies.constant.Constant
import com.smartr.smartrmovies.model.SearchMovies
import com.smartr.smartrmovies.model.TrendingData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

  companion object {
    const val BASE_URL = "https://api.themoviedb.org/3/"
  }

  @GET(Constant.END_POINT_TRENDING)
  @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYzkxN2ZjMWIxMTgxNTY4NDJiMzYyN2ZmODEwY2Q4YyIsInN1YiI6IjVhYTAxZWQzMGUwYTI2MzI3YTAxMGEzMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.W5PptCGyk0S9lHOADzCZR2dD1ZrKHspNlSLrmvsexGE")
  suspend fun getTrending() : TrendingData


  @GET("search/tv")
  @Headers("Authorization: Bearer " + Constant.AUTH_TOKEN)
  suspend fun searchMovies(@Query("query") key: String) : SearchMovies

  @GET("movie/{path}/similar?language=en-US&page=1")
  @Headers("Authorization: Bearer " + Constant.AUTH_TOKEN)
  suspend fun showSimilarMovies(@Path("path")key: String) : TrendingData
}