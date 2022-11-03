package com.foody.foody.ui.splash

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foody.foody.utils.SessionManagement
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    val liveSessionFlow = MutableLiveData<Boolean>()

    private val sessionManagement: SessionManagement by lazy {
        SessionManagement(context)
    }

    init {
        viewModelScope.launch {
            delay(500) //TODO
            checkIsLogged()
        }
    }

    private fun checkIsLogged() {
        if (sessionManagement.checkIsLogged())
            liveSessionFlow.postValue(true)
        else
            liveSessionFlow.postValue(false)
    }
}