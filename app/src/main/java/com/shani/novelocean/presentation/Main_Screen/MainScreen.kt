package com.shani.novelocean.presentation.Main_Screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.shani.novelocean.presentation.Explore_Screen.ExploreScreen
import com.shani.novelocean.presentation.Home_Screen.HomeScreen
import com.shani.novelocean.presentation.Library_Screen.LibraryScreen
import com.shani.novelocean.presentation.Profile_Screen.ProfileScreen
import com.shani.novelocean.presentation.nav_graph.Route

@Composable
fun MainScreen(){

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ){ paddingValues ->

        val graph =
            navController.createGraph(startDestination = Route.Home.route){
                composable(route = Route.Home.route) {
                    HomeScreen(
                        onBookClick = {bookId->
                            navController.navigate("book_detail/$bookId")

                        }
                    )
                }

                composable(
                    route ="${Route.BookDetailScreen.route}/{bookId}",
                    arguments = listOf(navArgument("bookId"){ type = NavType.StringType})
                ){navBackStackEntry ->
                    val bookId = navBackStackEntry.arguments?.getString("bookId") ?: ""

                    Route.BookDetailScreen()

                }
                composable(route = Route.Explore.route){ ExploreScreen() }
                composable(route = Route.Library.route){ LibraryScreen() }
                composable(route = Route.Profile.route){ ProfileScreen() }
            }

        NavHost(
            navController = navController,
            graph = graph,
            modifier =  Modifier.padding(paddingValues)
        )


    }

}