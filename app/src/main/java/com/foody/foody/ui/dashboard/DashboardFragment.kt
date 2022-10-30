package com.foody.foody.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.foody.foody.R
import com.foody.foody.adapters.DashboardMealAdapter
import com.foody.foody.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var mealAdapter: DashboardMealAdapter

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
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        Log.d("TAG2", p2.toString())
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
            }
        })

        dashboardBinding.recyclerDashboardFragment.apply {
            mealAdapter = DashboardMealAdapter(context)
            adapter = mealAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        viewModel.meals.observe(viewLifecycleOwner, Observer { item ->
            Log.d("TAG", item.body()?.meals.toString())
            mealAdapter.submitList(item.body()?.meals)
        })

    }

}