package com.shani.novelocean.presentation.nav_graph

 sealed class Route(val route: String){
     object Home: Route("home_screen")
     object Profile: Route("profile_screen")
     object Library: Route("library_screen")
     object Explore: Route("explore_screen")
     object BookDetailScreen: Route("book_detail")
     object ReaderScreen : Route("reader_screen")
}