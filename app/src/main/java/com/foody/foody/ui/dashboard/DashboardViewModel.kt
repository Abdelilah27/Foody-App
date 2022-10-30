package com.foody.foody.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foody.foody.model.ListCategory
import com.foody.foody.model.ListMeal
import com.foody.foody.repository.RetrofitServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(private val retrofitServiceRepository: RetrofitServiceRepository) :
    ViewModel() {
    private val _categories = MutableLiveData<Response<ListCategory>>()
    val categories: LiveData<Response<ListCategory>> = _categories

    private val _meals = MutableLiveData<Response<ListMeal>>()
    val meals: LiveData<Response<ListMeal>> = _meals

    init {
        viewModelScope.launch {
            val child = launch { getCategories() }
            child.join()
            delay(500)
            launch {
                if (child.isCompleted) {
                    getDataByCategory(
                        categories.value?.body()?.categories?.first()
                            ?.strCategory.toString()
                    )
                }
            }
        }
    }

    private suspend fun getCategories() {
        val categories = retrofitServiceRepository.getCategoriesFromAPI()
        _categories.postValue(categories)
    }

    suspend fun getDataByCategory(category: String) {
        val meals = retrofitServiceRepository.getDataByCategoryFromAPI(category)
        _meals.postValue(meals)
    }

}