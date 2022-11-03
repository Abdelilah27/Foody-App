package com.foody.foody.ui.registration

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foody.foody.R
import com.foody.foody.model.User
import com.foody.foody.model.UserError
import com.foody.foody.repository.RoomRepository
import com.foody.foody.utils.FunUtil
import com.foody.foody.utils.FunUtil.toSHA256Hash
import com.foody.foody.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: RoomRepository) :
    ViewModel() {
    private val _liveUserData = MutableLiveData<User>(User())
    val liveUser: LiveData<User> = _liveUserData

    private val _liveUserError = MutableLiveData<UserError>(UserError())
    val liveErrorUser: LiveData<UserError> = _liveUserError

    val isChecked = ObservableField<Boolean>(false)

    // Registration updates
    val liveRegistrationFlow = MutableLiveData<Resource.Status>(Resource.Status.NONE)

    fun onRegistrationClicked(confirmPassword: String): Boolean {
        var isValid = true
        if (liveUser.value?.name.isNullOrEmpty()) {
            _liveUserError.postValue(UserError(nameError = R.string.name_error))
            isValid = false
        }
        if (isValid && liveUser.value?.email.isNullOrEmpty()) {
            _liveUserError.postValue(UserError(emailError = R.string.email_error))
            isValid = false
        }
        if (isValid && !FunUtil.isEmailValid(liveUser.value?.email!!)) {
            _liveUserError.postValue(UserError(emailError = R.string.email_error))
            isValid = false
        }
        if (isValid && liveUser.value?.password.isNullOrEmpty()) {
            _liveUserError.postValue(UserError(passwordError = R.string.password_error))
            isValid = false
        }
        if (isValid && !isPasswordLengthGreaterThan5(liveUser.value?.password!!)) {
            _liveUserError.postValue(UserError(passwordError = R.string.password_error_regex))
            isValid = false
        }

        if (isValid && confirmPassword.isEmpty()) {
            _liveUserError.postValue(
                UserError(
                    confirmPasswordError = R.string
                        .verification_password_error
                )
            )
            isValid = false
        }
        if (isValid && confirmPassword != liveUser.value?.password) {
            _liveUserError.postValue(UserError(confirmPasswordError = R.string.verification_password_not_match_error))
            isValid = false
        }

        if (isValid && isChecked.get() == false) {
            _liveUserError.postValue(UserError(checkBoxError = R.string.checkBox_error))
            isValid = false
        }

        if (isValid) {
            viewModelScope.launch {
                val listUser = repository.findUserByEmail(liveUser.value!!.email)
                if (listUser?.isNotEmpty() == true) {
                    _liveUserError.postValue(UserError(emailError = R.string.email_exist_error))
                } else if (listUser?.isEmpty() == true) {
                    liveRegistrationFlow.postValue(Resource.Status.LOADING)
                    val userInfo =
                        liveUser.value!!.copy(password = liveUser.value!!.password.toSHA256Hash())
                    repository.insert(userInfo)
                    liveRegistrationFlow.postValue(Resource.Status.SUCCESS)
                }
            }
        }



        return isValid
    }


    private fun isPasswordLengthGreaterThan5(password: String): Boolean {
        return password.length > 5
    }

}

