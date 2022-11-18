package com.foody.foody.ui.details

import android.content.Context
import androidx.lifecycle.*
import com.foody.foody.model.MealDetails
import com.foody.foody.repository.RetrofitServiceRepository
import com.foody.foody.utils.ConstUtil
import com.foody.foody.utils.FunUtil
import com.foody.foody.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val retrofitServiceRepository: RetrofitServiceRepository,
    @ApplicationContext private val context: Context,
    private val state: SavedStateHandle
) : ViewModel() {
    private val _mealsDetails = MutableLiveData<Response<MealDetails>>()
    val mealsDetails: LiveData<Response<MealDetails>> = _mealsDetails

    // Handle error
    val liveDataFlow: MutableLiveData<NetworkResult<MealDetails>> = MutableLiveData()

    init {
        viewModelScope.launch {
            // Get the id from args and savedStateHandle
            // Remove the dashboardFragment suffix
            val mealId = state.get<String>("mealId")!!.removeSuffix(ConstUtil.dashboardFragment)
            getDetailsById(mealId)
        }
    }

    private suspend fun getDetailsById(id: String) {
        liveDataFlow.postValue(NetworkResult.Loading())
        try {
            if (FunUtil.hasInternetConnection(context)) {
                val mealDetails = retrofitServiceRepository.getDetailsById(id)
                _mealsDetails.postValue(mealDetails)
                liveDataFlow.postValue(NetworkResult.Success(mealDetails.body()!!))

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
}