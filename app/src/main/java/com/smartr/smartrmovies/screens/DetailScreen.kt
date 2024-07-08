package com.smartr.smartrmovies.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage


import com.smartr.smartrmovies.networkUtils.NetworkResult
import com.smartr.smartrmovies.viewModel.SimilarViewModel
import com.smartr.smartrmovies.R
import com.smartr.smartrmovies.constant.Constant

@Composable
fun DetailsScreen(
    originalTitle: String?,
    description: String?,
    overview: String?,
    voteAverage: String?,
    id: String?,
    similarViewModel: SimilarViewModel,
    navController: NavHostController,
) {
    Log.d("Deepak detail", description.toString())
    Log.d("Deepak detail", originalTitle.toString())
    Log.d("Deepak detail", overview.toString())
    Log.d("Deepak detail", voteAverage.toString())


    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "$originalTitle", style = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace
            )
        )
        AsyncImage(
            model = Constant.BASE_URL_IMAGE_O.plus(description),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "The delasign logo",
            contentScale = ContentScale.Fit,

            modifier = Modifier
                .padding(1.dp)
                .height(300.dp)
                .background(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$overview",
            style = TextStyle(
                color = Color.White,
                fontSize = 13.sp,

                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())

        )

        /* Column(
             verticalArrangement = Arrangement.Bottom,
             horizontalAlignment = Alignment.CenterHorizontally,

             modifier = Modifier
                 .fillMaxSize()
                 .background(Color.Black)
                 .padding(16.dp)
                 .height(150.dp)
         ) {

         }*/
        LaunchedEffect(key1 = true,) {
        similarViewModel.getSimilarDataDetails(id!!)
    }

        when (val result = similarViewModel.responseSim.value) {
            is NetworkResult.Success -> {
                Text(text = "Similar TV Shows",
                    style = TextStyle(
                        color = Color.White,

                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ))

                LazyRow {
                    Log.d(" Akash similar ", result.dataSealed.results.size.toString())

                    items(result.dataSealed.results) { response ->
                        EachRowSimilar(post = response, navController)
                    }
                }
                Log.d("akku", "success")
            }

            is NetworkResult.Failure -> {
                Text(text = "${result.msg}")
                Log.d("akku", "fail ")

            }

            NetworkResult.Loading -> {
                CircularProgressIndicator()
                Log.d("akku", "load ")

            }

            NetworkResult.Empty -> {
                Log.d("akku", "empty ")

            }
        }
    }


}
