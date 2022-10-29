package com.foody.foody.ui.dashboard

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.foody.foody.R
import com.foody.foody.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val viewModel: DashboardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        dashboardBinding.dashboardViewModel = viewModel
        dashboardBinding.lifecycleOwner = this
        initUI(dashboardBinding)
        return dashboardBinding.root
    }

    private fun initUI(dashboardBinding: FragmentDashboardBinding) {
        viewModel.categories.observe(viewLifecycleOwner, Observer { item ->
            // fil the spinner with the categories data
            val listCategoriesName: ArrayList<String> = ArrayList()
            item.body()!!.categories.forEach {
                listCategoriesName.add(it.strCategory)
            }
            val adapters = activity?.let {
                ArrayAdapter(
                    it.applicationContext,
                    android.R.layout.simple_spinner_item,
                    listCategoriesName
                )
            } as SpinnerAdapter
            with(dashboardBinding.spinnerDashboardFragment)
            {
                adapter = adapters
                setSelection(0, false)
                gravity = Gravity.CENTER
            }
        })

    }

}