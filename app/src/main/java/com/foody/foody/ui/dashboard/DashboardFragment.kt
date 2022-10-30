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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.foody.foody.R
import com.foody.foody.adapters.DashboardMealAdapter
import com.foody.foody.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


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
        initUI(dashboardBinding)
        return dashboardBinding.root
    }

    private fun initUI(dashboardBinding: FragmentDashboardBinding) {
        viewModel.categories.observe(viewLifecycleOwner, Observer { item ->
            // Fill the spinner with the categories data
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
                        // Notify adapter and change data by categories
                        lifecycleScope.launch {
                            getDataByCategory(listCategoriesName[p2])
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }
            }
        })

        // Setup our recycler
        dashboardBinding.recyclerDashboardFragment.apply {
            mealAdapter = DashboardMealAdapter(context)
            adapter = mealAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

        viewModel.meals.observe(viewLifecycleOwner, Observer { item ->
            try {
                mealAdapter.setData(item.body()?.meals!!)
            } catch (e: Exception) {
                Log.d("Execption", e.toString())
            }
        })

    }

    private suspend fun getDataByCategory(category: String) {
        viewModel.getDataByCategory(category)
    }


}