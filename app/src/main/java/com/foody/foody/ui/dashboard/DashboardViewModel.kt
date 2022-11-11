package com.foody.foody.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foody.foody.model.ListCategory
import com.foody.foody.model.ListMeal
import com.foody.foody.repository.RetrofitServiceRepository
import com.foody.foody.utils.FunUtil.hasInternetConnection
import com.foody.foody.utils.NetworkResult
import com.foody.foody.utils.OnItemSelectedInterface
import com.foody.foody.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val retrofitServiceRepository:
    RetrofitServiceRepository, @ApplicationContext private val context: Context
) :
    ViewModel(), OnItemSelectedInterface {
    private val _categories = MutableLiveData<Response<ListCategory>>()
    val categories: LiveData<Response<ListCategory>> = _categories

    private val _meals = MutableLiveData<Response<ListMeal>>()
    val meals: LiveData<Response<ListMeal>> = _meals

    // Handle error
    val liveCategoriesFlow: MutableLiveData<NetworkResult<ListCategory>> = MutableLiveData()
    val liveDataFlow: MutableLiveData<NetworkResult<ListMeal>> = MutableLiveData()

    //  To Save Selected Spinner Position
    val preferenceManger: PreferenceManager by lazy {
        PreferenceManager(context)
    }

    init {
        // Init live flow
        viewModelScope.launch {
            // Get categories for the spinner
            getCategories()
            getDataByCategory(_categories.value?.body()?.categories?.first()?.strCategory.toString())
        }

    }

    suspend fun getDataByCategory(category: String) {
        liveDataFlow.postValue(NetworkResult.Loading())
        try {
            if (hasInternetConnection(context)) {
                val meals = retrofitServiceRepository.getDataByCategoryFromAPI(category)
                _meals.postValue(meals)
                liveDataFlow.postValue(NetworkResult.Success(meals.body()!!))

            } else {
                liveDataFlow.postValue(NetworkResult.Error("No Internet Connection"))
            }

        } catch (ex: Exception) {
            when (ex) {
                is IOException -> {
                    liveDataFlow.postValue(
                        NetworkResult.Error(
                            "Network " +
                                    "Failure " + ex.localizedMessage
                        )
                    )
                }
                else -> {
                    liveDataFlow.postValue(NetworkResult.Error("Conversion Error"))
                }
            }
        }

    }

    private suspend fun getCategories() {
        liveCategoriesFlow.postValue(NetworkResult.Loading())
        try {
            if (hasInternetConnection(context)) {
                val categories = retrofitServiceRepository.getCategoriesFromAPI()
                _categories.postValue(categories)
                liveCategoriesFlow.postValue(NetworkResult.Success(categories.body()!!))
            } else {
                liveCategoriesFlow.postValue(NetworkResult.Error("No Internet Connection"))
            }
        } catch (ex: Exception) {
            when (ex) {
                is IOException -> {
                    liveCategoriesFlow.postValue(
                        NetworkResult.Error(
                            "Network " +
                                    "Failure " + ex.localizedMessage
                        )
                    )
                }
                else -> {
                    liveCategoriesFlow.postValue(NetworkResult.Error("Conversion Error"))
                }
            }
        }
        delay(100) //TODO
    }

    override suspend fun onItemClick(position: Int) {
        getDataByCategory(this._categories.value?.body()?.categories?.get(position).toString())
    }
}