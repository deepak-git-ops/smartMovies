package com.smartr.smartrmovies.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartr.smartrmovies.networkUtils.NetworkResultS
import com.smartr.smartrmovies.repository.TrendingMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: TrendingMovieRepository) :
 ViewModel(){
 val responseS: MutableState<NetworkResultS> = mutableStateOf(NetworkResultS.Empty)

 fun getSearchDetails(querys: String) =
  viewModelScope.launch {
   repository.fetchSearchData(querys).onStart {

    responseS.value = NetworkResultS.Loading
    Log.d("Akash", responseS.value.toString())
   }.catch {
    responseS.value = NetworkResultS.Failure(it)
   }.collect {
    responseS.value = NetworkResultS.Success(it)
    Log.d(" search success", responseS.value.toString())

   }
  }
}