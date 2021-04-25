package com.app.covidhelp.api


import retrofit2.Response
import retrofit2.http.GET

interface API {


    @GET("fetch_tweets")
    suspend fun getTweets(): Response<APIREsponse>

}