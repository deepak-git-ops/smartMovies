package com.smartr.smartrmovies.navigationUtils

sealed class NavRoutes(val routes: String) {

    object MovieScreen: NavRoutes("Home")
    object DetailScreen: NavRoutes("Detail")

}

