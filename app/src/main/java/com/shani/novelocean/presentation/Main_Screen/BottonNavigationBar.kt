package com.shani.novelocean.presentation.Main_Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shani.novelocean.R
import com.shani.novelocean.presentation.nav_graph.Navigation_Item.BottomNavItem
import com.shani.novelocean.presentation.nav_graph.Route

@Composable
fun BottomNavigationBar(navController: NavController){

    val selectedNavigationItem = rememberSaveable{
        mutableIntStateOf(0)
    }

    val navigationItems = listOf(
        BottomNavItem(
            title = "Home",
            icon =  Icons.Default.Home,
            route = Route.Home.route
        ),
        BottomNavItem(
            title = "Explore",
            icon = Icons.Default.DataExploration,
            route = Route.Explore.route
        ),
        BottomNavItem(
            title = "Library",
            icon = Icons.Default.Book,
            route = Route.Library.route
        ),
        BottomNavItem(
            title = "Profile",
            icon = Icons.Default.AccountCircle,
            route = Route.Profile.route
        ),

    )
    NavigationBar (
        containerColor = MaterialTheme.colorScheme.primary
    ){

        navigationItems.forEachIndexed{index, bottomNavItem ->
            NavigationBarItem(
                selected = selectedNavigationItem.intValue == index,
                onClick = {
                    selectedNavigationItem.intValue = index
                    navController.navigate(bottomNavItem.route)
                },
                icon = {
                       Icon(imageVector = bottomNavItem.icon,
                           contentDescription = bottomNavItem.title,
                           tint = Color.DarkGray,
                           modifier = Modifier.size(30.dp))
                },
                label = {
                    Text(
                        bottomNavItem.title,
                        color = if (index == selectedNavigationItem.intValue)
                            colorResource(R.color.indicator)
                        else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = colorResource(R.color.indicator)
                )
            )

        }

    }
}