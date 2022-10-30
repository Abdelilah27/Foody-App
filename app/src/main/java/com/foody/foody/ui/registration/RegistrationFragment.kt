package com.foody.foody.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.foody.foody.R
import com.foody.foody.databinding.FragmentRegistrationBinding
import com.foody.foody.utils.BinderUtil.hideKeyboard
import com.foody.foody.utils.PIBaseActivity
import com.foody.foody.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val registerBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        registerBinding.registrationViewModel = viewModel
        registerBinding.lifecycleOwner = this
        initUI(registerBinding)
        return registerBinding.root
    }

    private fun initUI(binding: FragmentRegistrationBinding) {
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

                        val action =
                            RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
                        findNavController().navigate(action)
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.liveRegistrationFlow.removeObservers(viewLifecycleOwner)
        view?.hideKeyboard()
    }
}