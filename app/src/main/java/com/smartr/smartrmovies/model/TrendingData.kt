package com.smartr.smartrmovies.model



data class TrendingData(val page: Int, val results: List<Result>, val total_pages: Int,
                        val total_results: Int)

data class ApiResponse(
    val data: TrendingData, val result: List<Result>)