package com.foody.foody.ui.registration

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.foody.foody.R
import com.foody.foody.databinding.FragmentRegistrationBinding
import com.foody.foody.utils.PIBaseActivity
import com.foody.foody.utils.Resource
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

            viewModel.liveRegistrationFlow.observe(viewLifecycleOwner, Observer {
                when (it) {
                    Resource.Status.LOADING -> {
                        (activity as PIBaseActivity).showProgressDialog("Registration")
                    }
                    Resource.Status.SUCCESS -> {
                        (activity as PIBaseActivity).dismissProgressDialog("Registration")

//                        val action =
//                            RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
//                        findNavController().navigate(action)
//                        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
//                        MainActivity.navController.navigate(R.id.action_registrationFragment_to_loginFragment)
                    }
                    else -> {
                        (activity as PIBaseActivity).dismissProgressDialog("Registration")
                    }
                }

            })
        }


        //On Text Login Click
        binding.loginTextRegistrationFragment.setOnClickListener {
            val action =
                RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
            findNavController().navigate(action)
        }

    }
}