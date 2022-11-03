package com.foody.foody.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.foody.foody.MainActivity
import com.foody.foody.R
import com.foody.foody.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {
    private val viewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val accountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        accountBinding.accountViewModel = viewModel
        accountBinding.lifecycleOwner = this
        initUI(accountBinding)
        return accountBinding.root
    }

    private fun initUI(accountBinding: FragmentAccountBinding) {
        accountBinding.nameAccountFragment.text = viewModel.getName()
        accountBinding.emailAccountFragment.text = viewModel.getEmail()

        viewModel.liveLogoutFlow.observe(viewLifecycleOwner, Observer {
            if (it) {
                MainActivity.navController.navigate(
                    R.id.splashScreenFragment
                )
            }
        })

    }
}