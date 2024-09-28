package com.mohamed.data.remote

import com.mohamed.data.models.CitiesResponse
import retrofit2.http.GET

interface CitiesApi {

    // override the base url for specific api
    @GET("https://dev-orcas.s3.eu-west-1.amazonaws.com/uploads/cities.json")
    suspend fun getCities(): CitiesResponse
}