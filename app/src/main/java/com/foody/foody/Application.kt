package com.foody.foody

import android.app.Application
import com.foody.foody.model.User
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    companion object {
        var currUser: User? = null
    }
}