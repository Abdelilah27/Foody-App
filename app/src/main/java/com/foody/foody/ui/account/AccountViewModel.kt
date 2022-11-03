package com.foody.foody.ui.account

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foody.foody.utils.SessionManagement
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    val liveLogoutFlow = MutableLiveData<Boolean>(false) // to Observe the logout action

    private val sessionManagement: SessionManagement by lazy {
        SessionManagement(context)
    }

    fun getName(): String = sessionManagement.getName()

    fun getEmail(): String = sessionManagement.getEmail()

    fun onLogoutClicked() {
        sessionManagement.logOut()
        liveLogoutFlow.postValue(true)
    }

}