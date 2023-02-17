package com.example.movieapp.movie

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                MainContent()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            SmallTopAppBar(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primary),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Movie List",
                            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        )
                    }
                }
            )

        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Latest.screenRoute
    ) {
        composable(BottomNavigationItem.Latest.screenRoute) {
            LatestMovieScreen(movieListViewModel = movieListViewModel)
        }
        composable(BottomNavigationItem.Popular.screenRoute) {
            PopularMovieScreen(movieListViewModel = movieListViewModel)
        }
    }

}

@Composable
fun BottomNavigation(navController: NavController) {
    val bottomList = listOf(
        BottomNavigationItem.Latest,
        BottomNavigationItem.Popular,
    )
    var selectedItem by remember {
        mutableStateOf(0)
    }

    NavigationBar {
        bottomList.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = bottomNavigationItem.icon),
                        contentDescription = "Icon"
                    )
                },
                label = { Text(text = bottomNavigationItem.title) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(
                        bottomNavigationItem.screenRoute
                    ) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(
                                screenRoute
                            ) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}