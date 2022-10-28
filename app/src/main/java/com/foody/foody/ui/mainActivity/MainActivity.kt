package com.foody.foody.ui.mainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.foody.foody.R
import com.foody.foody.utils.PIBaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : PIBaseActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

}