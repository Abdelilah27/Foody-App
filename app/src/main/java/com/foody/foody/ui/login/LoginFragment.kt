package com.foody.foody.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.foody.foody.R
import com.foody.foody.databinding.FragmentLoginBinding
import com.foody.foody.utils.PIBaseActivity
import com.foody.foody.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        loginBinding.loginViewModel = viewModel
        loginBinding.lifecycleOwner = this
        initUI(loginBinding)
        return loginBinding.root
    }

    private fun initUI(loginBinding: FragmentLoginBinding) {
        viewModel.liveLoginFlow.observe(viewLifecycleOwner, Observer {
            when (it) {
                Resource.Status.LOADING -> {
                    (activity as PIBaseActivity).showProgressDialog("Login")
                }
                Resource.Status.SUCCESS -> {
                    (activity as PIBaseActivity).dismissProgressDialog("Login")
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToBottomNavigationFragment()
                    findNavController().navigate(action)
                }
                else -> {
                    (activity as PIBaseActivity).dismissProgressDialog("Login")
                }
            }

        })

        // On Registration text Click
        loginBinding.registrationTextLoginFragment.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
    }

}