package com.smartr.smartrmovies.model


data class SearchMovies(val page: Int,

                        val results: List<SearchResult>,
                        val total_pages: Int,
                        val total_results: Int) {

}
