package com.foody.foody.network

import com.foody.foody.model.ListCategory
import com.foody.foody.model.ListMeal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceInterface {
    @GET("categories.php")
    suspend fun getCategoriesFromAPI(): Response<ListCategory>

    @GET("filter.php")
    suspend fun getDataByCategoryFromAPI(@Query("c") category: String): Response<ListMeal>
}