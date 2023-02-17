package com.example.movieapp.movie

import com.example.movieapp.R

sealed class BottomNavigationItem(
    var title:String,var icon:Int, var screenRoute:String
) {
    object Latest: BottomNavigationItem("Latest", R.drawable.latest_movie, "latest")
    object Popular: BottomNavigationItem("Popular", R.drawable.popular_movie, "popular")
}
