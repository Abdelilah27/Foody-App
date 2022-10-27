package com.foody.foody.ui.registration

import android.util.Log
import android.util.Patterns
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foody.foody.R
import com.foody.foody.model.User
import com.foody.foody.model.UserError
import com.foody.foody.repository.RoomRepository
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: RoomRepository) :
    ViewModel() {
    private val _liveUserData = MutableLiveData<User>(User())
    val liveUser: LiveData<User> = _liveUserData

    private val _liveUserError = MutableLiveData<UserError>(UserError())
    val liveErrorUser: LiveData<UserError> = _liveUserError

    val isChecked = ObservableField<Boolean>(false)

    fun onRegistrationClicked(confirmPassword: String): Boolean {
        Log.d("TAG", "is checked: ${isChecked.get()}")
        var isValid = true
        if (liveUser.value?.name.isNullOrEmpty()) {
            _liveUserError.postValue(UserError(nameError = R.string.name_error))
            isValid = false
        }
        if (isValid && liveUser.value?.email.isNullOrEmpty()) {
            _liveUserError.postValue(UserError(emailError = R.string.email_error))
            isValid = false
        }
        if (isValid && !isEmailValid(liveUser.value?.email!!)) {
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
            _liveUserError.postValue(UserError(checkBoxError = false))
            isValid = false
        }




        if (!isValid) {
        }



        return isValid
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    private fun isPasswordLengthGreaterThan5(password: String): Boolean {
        return password.length > 5
    }


    @BindingAdapter("app:errorText")
    fun setErrorMessage(view: TextInputLayout, errorMessage: Int) {
        view.error = errorMessage.toString()
    }
}

