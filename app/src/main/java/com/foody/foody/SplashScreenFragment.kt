package com.foody.foody

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.foody.foody.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSplashScreenBinding.bind(view)
        Handler().postDelayed({
            val action =
                SplashScreenFragmentDirections.actionSplashScreenFragmentToWelcomeFragment()
            findNavController().navigate(action)
        }, 1500)
    }
}