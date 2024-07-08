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
class SimilarViewModel @Inject constructor(private val repository: TrendingMovieRepository) :
 ViewModel() {

     val responseSim: MutableState<NetworkResult> = mutableStateOf(NetworkResult.Empty)

    fun getSimilarDataDetails(id: String) =  viewModelScope.launch{
        repository.fetchSimilarData(id!!).onStart {
            responseSim.value = NetworkResult.Loading
            Log.d("Deepak", responseSim.value.toString())
        }.catch {
            responseSim.value = NetworkResult.Failure(it)
        }.collect{
            responseSim.value = NetworkResult.Success(it)
            Log.d("Deepak success", responseSim.value.toString())
        }
    }
}