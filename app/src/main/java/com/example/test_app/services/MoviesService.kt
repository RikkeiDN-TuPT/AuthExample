package com.example.test_app.services

import com.example.test_app.data.models.ApiMovieDBResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("discover/movie")
    fun getListMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int? = 1,
        @Query("with_watch_monetization_types") type: String? = "flatrate",
        @Query("sort_by") sort_by: String? = "popularity.desc",
        @Query("include_adult") include_adult: Boolean? = false,
        @Query("include_video") include_video: Boolean? = false,
        @Query("language") language: String? = "vi-VN"
    ): Call<ApiMovieDBResult>
}