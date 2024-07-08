package com.smartr.smartrmovies.utils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.smartr.smartrmovies.viewModel.SearchViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.ui.unit.dp
import com.smartr.smartrmovies.networkUtils.NetworkResultS
import com.smartr.smartrmovies.screens.EachRowForSearch

@Composable
fun SearchField(searchViewModel : SearchViewModel, navHostController : NavHostController) {

    val query: MutableState<String> = remember { mutableStateOf("") }
    val result = searchViewModel.responseS.value

    Surface(
        modifier = Modifier
            .height(60.dp)
            .background(Color.Black)
    ) {

        TextField(
            value = query.value,
            onValueChange = {

                query.value = it
                Log.d("dpk", it)
                searchViewModel.getSearchDetails(query.value)
            },
            enabled = true,
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                if (query.value.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            // Remove text from TextField when you press the 'X' icon
                            query.value = ""
                            searchViewModel.getSearchDetails(query.value)
                        }) {

                        Icon(
                            Icons.Default.Close, contentDescription = null,
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },

            label = { Text(text = "Search here..") },
            //colors = TextFieldDefaults.textFieldColors(textColor = Color.Black),
            modifier = Modifier.fillMaxWidth(),
        )
    }


    when(result) {
        is NetworkResultS.Success -> {
            LazyColumn() {
                items(result.dataSealed.results) { response ->
                    EachRowForSearch(post = response, navHostController)
                }
            }
        }

        is NetworkResultS.Loading -> {
            androidx.compose.material3.CircularProgressIndicator()
        }

        is NetworkResultS.Empty -> {
            Log.d("Deepak", "empty ")

        }

        else -> {}
    }

}

