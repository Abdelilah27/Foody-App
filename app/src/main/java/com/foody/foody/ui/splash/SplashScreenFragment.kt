package com.foody.foody.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.foody.foody.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
    private val viewModel: SplashViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveSessionFlow.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    val action =
                        SplashScreenFragmentDirections.actionSplashScreenFragmentToBottomNavigationFragment()
                    findNavController().navigate(action)
                }
                else -> {
                    val action =
                        SplashScreenFragmentDirections.actionSplashScreenFragmentToWelcomeFragment()
                    findNavController().navigate(action)
                }
            }
        })

    }
}