package com.smartr.smartrmovies.screens

import android.icu.text.DecimalFormat
import android.util.Log

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.smartr.smartrmovies.R
import com.smartr.smartrmovies.constant.Constant.BASE_URL_IMAGE
import com.smartr.smartrmovies.model.SearchResult
import com.smartr.smartrmovies.navigationUtils.NavRoutes
import com.smartr.smartrmovies.networkUtils.NetworkResult

import com.smartr.smartrmovies.viewModel.SearchViewModel
import com.smartr.smartrmovies.viewModel.TrendingViewModel
import com.smartr.smartrmovies.model.Result
import com.smartr.smartrmovies.utils.SearchField

import java.math.RoundingMode

@Composable
fun MovieScreen(navHostControler : NavHostController, trendingViewModel: TrendingViewModel,
                searchViewModel: SearchViewModel) {

    Column(
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color.Black)
    ) {

        Box(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)) {
            SearchField(searchViewModel, navHostControler)
        }

        when (val result = trendingViewModel.response.value) {
            is NetworkResult.Success -> {
                LazyColumn(modifier = Modifier.padding(20.dp)) {
                    Log.d("Deepak", result.dataSealed.results.size.toString())
                    items(result.dataSealed.results) { data ->
                        EachRow(post = data, navHostControler)
                    }
                }
            }

            is NetworkResult.Failure -> {
                Text(text = "${result.msg}")
                Log.d("Deep", "fail ")
            }

            NetworkResult.Loading -> {
                CircularProgressIndicator()
                Log.d("Deep", "load ")
            }

            NetworkResult.Empty -> {
                Log.d("Deepak", "empty ")
            }
        }
    }
}

    @Composable
    fun EachRow(post: Result, navHostController: NavHostController) {
        Log.d("Deepak row", BASE_URL_IMAGE.plus(post?.backdrop_path))
        val df = DecimalFormat("#.#")
            df.roundingMode = 3//RoundingMode.FLOOR
            val rating = String.format("%1f", post.vote_average)

        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color.Black)) {
            Card(modifier = Modifier
                .width(320.dp)
                .height(300.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .background(Color.Black)
                .clickable {
                    navHostController.navigate(
                        NavRoutes.DetailScreen.routes +
                                "/${post.original_title ?: post.original_name}${post.backdrop_path}/" +
                                "${post.overview}/${post.vote_average}/${post.id}"
                    )
                },

                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                //elevation = 5dp,
                shape = RoundedCornerShape(24.dp)
              )
            {
                val matrix = ColorMatrix()
                matrix.setToSaturation(1F)
//                Column(verticalArrangement = Arrangement.Top,
//                    horizontalAlignment = Alignment.CenterHorizontally) {
                val imageModifier = Modifier.fillMaxSize()
                    //.size(150.dp)

                    .background(Color.Yellow)

                AsyncImage(
                    model = BASE_URL_IMAGE.plus(post?.backdrop_path),
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    error = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "The delasign logo",
                    contentScale = ContentScale.Crop,

                    modifier = imageModifier//Modifier.background(Color.Black),

                )
                //}

            Column(verticalArrangement = Arrangement.Bottom,
                  horizontalAlignment = Alignment.CenterHorizontally) {
                
                Text(text = post.original_title ?: post.original_name,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 10.dp)
                        .fillMaxWidth()
                        .drawBehind {
                            drawRect(Color.Gray, size = size)
                        },

                    style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Monospace)
                    )

                RatingBar(currentRating = rating.toDouble())
                Text(text = rating,
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawRect(
                                color = Color.Gray, size = size
                            )
                        },

                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }

            }
        }
    }


@Composable
fun EachRowForSearch(post : SearchResult, navHostController: NavHostController) {
    Log.d("Deepak row", BASE_URL_IMAGE.plus(post?.backdrop_path))
    val df = java.text.DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR

    val rating = String.format("%1f", post.vote_average)

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color.Black)
    ) {

        Card(
            modifier = Modifier
                .width(320.dp)
                .height(200.dp)
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                )
                .background(Color.Black)
                .clickable {
                    navHostController.navigate(
                        NavRoutes.DetailScreen.routes +
                                "/${post.name ?: post.original_name}${post.backdrop_path}/${post.overview}/" +
                                "${post.vote_average}/${post.id}"
                    )
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            //elevation = 5.dp,
            shape = RoundedCornerShape(24.dp)
        )
        {
            val matrix = ColorMatrix()
            matrix.setToSaturation(1F)

            AsyncImage(
                model = BASE_URL_IMAGE.plus(post?.backdrop_path),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "The delasign logo",
                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .background(color = Color.Black)
            )

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = post.name ?: post.original_name,
                    modifier = Modifier
                        .padding(0.dp, 60.dp, 0.dp, 30.dp)
                        .fillMaxWidth()
                        .drawBehind {
                            drawRect(
                                color = Color.Gray, size = size
                            )
                        },
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace
                    )
                )

                RatingBar(currentRating = rating.toDouble())
                Text(
                    text = rating,
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawRect(
                                color = Color.Gray, size = size
                            )
                        },

                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
        }
    }
}

@Composable
fun EachRowSimilar(post: Result, navHostController: NavHostController) {
    Log.d("Deepak row", BASE_URL_IMAGE.plus(post?.backdrop_path))
    val df = java.text.DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR

    val rating = String.format("%.1f", post.vote_average)

    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
            .clickable {
                navHostController.navigate(
                    NavRoutes.DetailScreen.routes +
                            "/${post.original_title ?: post.original_name}${post.backdrop_path}" +
                            "/${post.overview}/${post.vote_average}/${post.id}"
                )
            },
        //elevation = 5.dp,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(24.dp)
    ) {

        val matrix = ColorMatrix()
        matrix.setToSaturation(1F)
        AsyncImage(
            model = BASE_URL_IMAGE.plus(post?.backdrop_path) ?: error("Image not found"),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "The delasign logo",
            contentScale = ContentScale.Crop,

            modifier = Modifier
                .padding(1.dp)
                .background(color = Color.Black)
                .width(100.dp)
                .height(100.dp)
        )

        Text(text = post.original_title ?: post.original_name,
            modifier = Modifier
                .fillMaxWidth(),
            style = TextStyle(
                color = Color.White,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace
            )
        )
    }
}
