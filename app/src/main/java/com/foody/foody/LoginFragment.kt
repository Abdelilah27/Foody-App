package com.foody.foody

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.foody.foody.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)

        // On Login Button Click
        binding.mainButtonLoginFragment.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToBottomNavigationFragment()
            findNavController().navigate(action)
        }

        // On Registration text Click
        binding.registrationTextLoginFragment.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }


    }
}