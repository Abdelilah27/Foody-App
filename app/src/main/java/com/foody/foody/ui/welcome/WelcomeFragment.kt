package com.foody.foody.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.foody.foody.R
import com.foody.foody.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWelcomeBinding.bind(view)


        // Navigate to Next Screen
        binding.mainButtonWelcomeFragment.setOnClickListener {
            val action =
                WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}