package com.foody.foody.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.foody.foody.R
import com.google.android.material.textfield.TextInputLayout
import java.security.MessageDigest

object BinderUtil {
    @JvmStatic
    @BindingAdapter("app:errorText")
    fun setError(textInputLayout: TextInputLayout, error: Int?) {
        if (error == R.string.empty) {
            textInputLayout.error = null
        } else {
            textInputLayout.error = textInputLayout.context.getString(error!!)
            textInputLayout.requestFocus()
        }
    }

    @JvmStatic
    @BindingAdapter("app:errorText")
    fun setError(checkBox: CheckBox, error: Int?) {
        if (error == R.string.empty) {
            checkBox.error = null
        } else {
            checkBox.error = checkBox.context.getString(error!!)
            checkBox.requestFocus()
        }
    }

    fun String.toSHA256Hash(): String {
        val bytes = this.toString().toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}
