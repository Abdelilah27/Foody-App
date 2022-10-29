package com.foody.foody.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foody.foody.model.ListCategory
import com.foody.foody.repository.RetrofitServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(private val retrofitServiceRepository: RetrofitServiceRepository) :
    ViewModel() {
    private val categoriesLiveData = MutableLiveData<Response<ListCategory>>()
    val categories: LiveData<Response<ListCategory>> = categoriesLiveData

    init {
        viewModelScope.launch {
            val categories = retrofitServiceRepository.getCategoriesFromAPI()
            categoriesLiveData.postValue(categories)
        }
    }

}