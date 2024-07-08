package com.smartr.smartrmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import com.smartr.smartrmovies.CounterHelper.processInput
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smartr.smartrmovies.navigationUtils.NavRoutes
import com.smartr.smartrmovies.screens.DetailsScreen
import com.smartr.smartrmovies.screens.MovieScreen
import com.smartr.smartrmovies.ui.theme.SmartrMoviesTheme
import com.smartr.smartrmovies.viewModel.SearchViewModel
import com.smartr.smartrmovies.viewModel.SimilarViewModel
import com.smartr.smartrmovies.viewModel.TrendingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val trendingViewModel : TrendingViewModel by viewModels()
    private val searchViewModel : SearchViewModel by viewModels()
    private val similarViewModel : SimilarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SmartrMoviesTheme {
                val navController = rememberNavController()
                NavHost(navController = navController,
                    startDestination = NavRoutes.MovieScreen.routes) {
                    composable(NavRoutes.MovieScreen.routes) {
                        MovieScreen(navController, trendingViewModel, searchViewModel)
                    }

                    composable(NavRoutes.DetailScreen.routes + "/{name}/{description}/{overview}/" +
                            "{vote_average}/{id}") {backStackEntry ->
                        val restaurantName = backStackEntry.arguments?.getString("name")
                        val description = backStackEntry.arguments?.getString("description")
                        val overview = backStackEntry.arguments?.getString("overview")
                        val voteAverage = backStackEntry.arguments?.getString("vote_average")
                        val id = backStackEntry.arguments?.getString("id")
                        DetailsScreen(
                            restaurantName,
                            description,
                            overview,
                            voteAverage,
                            id, similarViewModel, navController
                        )
                    }
                }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Composable
fun CounterDisplay(
    modifier: Modifier
) {

    var editedText by remember {
        mutableStateOf("")
    }

    var counterText by remember {
        mutableStateOf("Start copying")
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.testTag("Counter Display"),
            text = counterText,
            style = TextStyle(
                fontSize = 36.sp,
                color = Color.Black
            )
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("Input"),
            value = editedText,
            onValueChange = {
                editedText = it
            },
            label = {
                Text("Input")
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                counterText = processInput(editedText)
            }
        ) {
            Text(
                text = "Copy",
                style = TextStyle(
                    fontSize = 26.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmartrMoviesTheme {
        //Greeting("Android")
        CounterDisplay(modifier = Modifier)
    }
}