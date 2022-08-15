package com.example.test_app.data.repository

import com.example.test_app.data.models.ApiMovieDBResult
import com.example.test_app.services.MoviesService
import com.example.test_app.services.RetrofitHelper
import com.example.test_app.utils.Constant
import retrofit2.Call
import retrofit2.Retrofit

class MovieRepository {
    private var retrofitClient: Retrofit = RetrofitHelper().getInstance(Constant.db_movie_url)
    fun getListMovies(): Call<ApiMovieDBResult> {
        val requestService = retrofitClient.create(MoviesService::class.java)
        return requestService.getListMovies(api_key = Constant.token_db_movie, page = 1)
    }

}