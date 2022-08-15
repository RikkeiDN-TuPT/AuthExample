package com.example.test_app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_app.data.models.ApiMovieDBResult
import com.example.test_app.data.models.MoviesDetails
import com.example.test_app.data.models.State.State
import com.example.test_app.data.repository.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    private var getListMovieState = MutableLiveData<State>()
    private lateinit var listMovies: List<MoviesDetails>

    init {
        getListMovieState.postValue(State.Loading(isLoading = true))
        MovieRepository().getListMovies().enqueue(object : Callback<ApiMovieDBResult> {
            override fun onResponse(
                call: Call<ApiMovieDBResult>,
                response: Response<ApiMovieDBResult>
            ) {
                getListMovieState.postValue(State.Loading(isLoading = false))
                try {
                    if (response != null) {
                        var result = response.body() as ApiMovieDBResult
                        var list = result.results
                        listMovies = list
                        getListMovieState.postValue(State.Successful(success = true))
                    } else {
                        getListMovieState.postValue(State.Successful(success = false))
                    }
                } catch (e: Exception) {
                    getListMovieState.postValue(State.Successful(success = false))
                    getListMovieState.postValue(State.Failure(msg = e.message.toString()))
                }

            }
            override fun onFailure(call: Call<ApiMovieDBResult>, t: Throwable) {
                getListMovieState.postValue(State.Loading(isLoading = false))
                getListMovieState.postValue(State.Successful(success = false))
                getListMovieState.postValue(State.Failure(msg = t.message.toString()))
                getListMovieState.postValue(State.Successful(success = false))
            }

        })
    }
}


