package com.foody.foody.network

import com.foody.foody.model.ListCategory
import com.foody.foody.model.Meal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitServiceInterface {
    @GET("categories.php")
    suspend fun getCategoriesFromAPI(): Response<ListCategory>


    @GET("api/json/v1/1/filter.php?c={category}")
    suspend fun getDataByCategoryFromAPI(@Path("category") category: String): Response<Meal>
}