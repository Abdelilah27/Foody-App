package com.foody.foody.ui.registration

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.foody.foody.R
import com.foody.foody.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.setContentView<FragmentRegistrationBinding>(
            requireActivity(),
            R.layout.fragment_registration
        )
        binding.lifecycleOwner = this
        binding.registrationViewModel = viewModel


        // On Registration Button Click
        binding.mainButtonRegistrationFragment.setOnClickListener {
            viewModel.onRegistrationClicked(
                binding.editTextVerificationPassRegistrationFragment
                    .text.toString()
            )
//            val action =
//                RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
//            findNavController().navigate(action)
        }

        viewModel.liveErrorUser.observe(viewLifecycleOwner, Observer {
            if (!it.checkBoxError) {
                Log.d("DDD", "onViewCreated: ")
            }
        })


        //On Text Login Click
        binding.loginTextRegistrationFragment.setOnClickListener {
            val action =
                RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
            findNavController().navigate(action)
        }

    }
}