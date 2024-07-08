package com.smartr.smartrmovies.networkUtils


import com.smartr.smartrmovies.model.SearchMovies


sealed class NetworkResultS {
    class Success(val dataSealed: SearchMovies) : NetworkResultS()//how to make generic response
    class Failure(val msg: Throwable) : NetworkResultS()
    object Loading:NetworkResultS()
    object Empty: NetworkResultS()
}