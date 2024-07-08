package com.smartr.smartrmovies.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartr.smartrmovies.networkUtils.NetworkResult
import com.smartr.smartrmovies.repository.TrendingMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: TrendingMovieRepository): ViewModel() {
    val response: MutableState<NetworkResult> = mutableStateOf(NetworkResult.Empty)
    
         init {
            getMovieDetails()
         }

    fun getMovieDetails() =
        viewModelScope.launch {
            repository.fetchData().onStart {
            response.value = NetworkResult.Loading
            }.catch {
                response.value = NetworkResult.Failure(it)
            }.collect {
                response.value = NetworkResult.Success(it)
                Log.d("Deepak success", response.value.toString())
            }
        }
}