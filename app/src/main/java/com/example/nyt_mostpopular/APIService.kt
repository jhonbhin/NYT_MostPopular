package com.example.nyt_mostpopular

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getMostPopularArticles(@Url url: String): Response<NYTJsonResponse>
}