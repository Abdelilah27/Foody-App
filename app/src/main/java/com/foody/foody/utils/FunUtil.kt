package com.foody.foody.utils

import android.util.Patterns

object FunUtil {
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}