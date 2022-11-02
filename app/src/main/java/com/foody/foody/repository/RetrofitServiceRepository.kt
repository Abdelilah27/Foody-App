package com.foody.foody.repository

import com.foody.foody.model.ListCategory
import com.foody.foody.model.ListMeal
import com.foody.foody.network.RetrofitServiceInterface
import retrofit2.Response
import javax.inject.Inject

class RetrofitServiceRepository @Inject constructor(private val retrofitServiceInterface: RetrofitServiceInterface) {

    suspend fun getCategoriesFromAPI(): Response<ListCategory> {
        return retrofitServiceInterface.getCategoriesFromAPI()
    }

    suspend fun getDataByCategoryFromAPI(category: String): Response<ListMeal> {
        return retrofitServiceInterface.getDataByCategoryFromAPI(category)
    }


}