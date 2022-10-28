package com.foody.foody.utils

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import com.foody.foody.R
import com.google.android.material.textfield.TextInputLayout

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
}
