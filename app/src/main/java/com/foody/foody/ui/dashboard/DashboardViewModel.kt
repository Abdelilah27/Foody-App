package com.foody.foody.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foody.foody.model.ListCategory
import com.foody.foody.model.ListMeal
import com.foody.foody.repository.RetrofitServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
            getCategories()
            getDataByCategory("Beef")
        }
    }

    private suspend fun getCategories() {
        val categories = retrofitServiceRepository.getCategoriesFromAPI()
        _categories.postValue(categories)
    }

    private suspend fun getDataByCategory(category: String) {
        val meals = retrofitServiceRepository.getDataByCategoryFromAPI(category)
        _meals.postValue(meals)
    }

}