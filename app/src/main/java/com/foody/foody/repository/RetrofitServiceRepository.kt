package com.foody.foody.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foody.foody.R
import com.foody.foody.model.ListCategory
import com.foody.foody.model.ListMeal
import com.foody.foody.network.RetrofitServiceInterface
import com.foody.foody.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class RetrofitServiceRepository @Inject constructor(private val retrofitServiceInterface: RetrofitServiceInterface) {

    // Error Handling LiveData getCategories
    private val _apiResponseCategoriesLiveData = MutableLiveData<NetworkResult<ListCategory>>()
    val apiResponseCategoriesLiveData: LiveData<NetworkResult<ListCategory>>
        get() = _apiResponseCategoriesLiveData


    // Error Handling LiveData getDataByCategory
    private val _apiResponseDataLiveData = MutableLiveData<NetworkResult<ListMeal>>()
    val apiResponseDataLiveData: LiveData<NetworkResult<ListMeal>>
        get() = _apiResponseDataLiveData

    suspend fun getCategoriesFromAPI(): Response<ListCategory> {
        _apiResponseCategoriesLiveData.postValue(NetworkResult.Loading())
        val response = retrofitServiceInterface.getCategoriesFromAPI()
        ErrorHandlingCategories(response)
        return response
    }

    // Error Handling getCategories
    private fun ErrorHandlingCategories(response: Response<ListCategory>) {
        if (response.isSuccessful && response.body() != null) {
            _apiResponseCategoriesLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _apiResponseCategoriesLiveData.postValue(
                NetworkResult.Error(
                    errorObj.getString
                        ("message")
                )
            )
        } else {
            _apiResponseCategoriesLiveData.postValue(NetworkResult.Error(R.string.retrofit_error.toString()))
        }
    }


    suspend fun getDataByCategoryFromAPI(category: String): Response<ListMeal> {
        _apiResponseDataLiveData.postValue(NetworkResult.Loading())
        val response = retrofitServiceInterface.getDataByCategoryFromAPI(category)
        ErrorHandlingData(response)
        return response
    }


    // Error Handling getCategories
    private fun ErrorHandlingData(response: Response<ListMeal>) {
        if (response.isSuccessful && response.body() != null) {
            _apiResponseDataLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _apiResponseDataLiveData.postValue(
                NetworkResult.Error(
                    errorObj.getString
                        ("message")
                )
            )
        } else {
            _apiResponseDataLiveData.postValue(NetworkResult.Error(R.string.retrofit_error.toString()))
        }
    }

}