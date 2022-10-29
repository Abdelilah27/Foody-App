package com.foody.foody.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foody.foody.Application
import com.foody.foody.R
import com.foody.foody.model.User
import com.foody.foody.repository.RoomRepository
import com.foody.foody.utils.BinderUtil.toSHA256Hash
import com.foody.foody.utils.FunUtil
import com.foody.foody.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RoomRepository) : ViewModel() {
    private val _liveUser =
        MutableLiveData<User>(User())
    val liveUser: LiveData<User> = _liveUser
    private val _errorUser: MutableLiveData<Int> = MutableLiveData(R.string.empty)
    val errorUser: LiveData<Int> = _errorUser
    private val _errorPass: MutableLiveData<Int> = MutableLiveData(R.string.empty)
    val errorPass: LiveData<Int> = _errorPass

    val liveLoginFlow = MutableLiveData<Resource.Status>(Resource.Status.NONE)


    fun onLoginClicked(): Boolean {
        var isValid = true
        if (liveUser.value?.email.isNullOrEmpty()) {
            _errorUser.postValue(R.string.email_error)
            isValid = false
        }

        if (isValid && !FunUtil.isEmailValid(liveUser.value?.email!!)) {
            _errorUser.postValue(R.string.email_error)
            isValid = false
        }

        if (isValid && liveUser.value?.password.isNullOrEmpty()) {
            _errorPass.postValue(R.string.password_error)
            isValid = false
        }

        if (isValid) {
            liveLoginFlow.postValue(Resource.Status.LOADING)
            viewModelScope.launch {
                val listUser = repository.findUserByEmailAndPass(
                    liveUser.value!!.email, liveUser.value!!.password.toSHA256Hash()
                )
                if (listUser?.isEmpty() == true) {
                    _errorUser.postValue(R.string.user_mail_pass_error)
                    liveLoginFlow.postValue(Resource.Status.ERROR)
                } else if (listUser?.isNotEmpty() == true) {
                    val userInfo = User(
                        id = listUser.first().id, name = listUser.first().name,
                        email = listUser
                            .first()
                            .email
                    )
                    Application.currUser = userInfo
                    liveLoginFlow.postValue(Resource.Status.SUCCESS)
                }
            }
        }

        return isValid
    }

}