package com.smartr.smartrmovies.networkUtils

import com.smartr.smartrmovies.model.TrendingData


sealed class NetworkResult {

    class Success(val dataSealed: TrendingData) : NetworkResult()//how to make generic response
    class Failure(val msg: Throwable) : NetworkResult()
    object Loading:NetworkResult()
    object Empty: NetworkResult()
}