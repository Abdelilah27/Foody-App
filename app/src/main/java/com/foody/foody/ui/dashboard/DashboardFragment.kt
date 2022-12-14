package com.foody.foody.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.foody.foody.MainActivity
import com.foody.foody.R
import com.foody.foody.adapters.DashboardMealAdapter
import com.foody.foody.databinding.FragmentDashboardBinding
import com.foody.foody.ui.bottomNavigation.BottomNavigationFragmentDirections
import com.foody.foody.utils.NetworkResult
import com.foody.foody.utils.PIBaseActivity
import com.foody.foody.utils.onItemSelectedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DashboardFragment() :
    Fragment(R.layout.fragment_dashboard), onItemSelectedAdapter {
    private val TAG = "DashboardFragment"
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var mealAdapter: DashboardMealAdapter
    private lateinit var dashboardBinding: FragmentDashboardBinding

    // to stock the category, number of elements name
    private var category = ""
    private var elementNumber = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        initUI(dashboardBinding)
        return dashboardBinding.root
    }

    override fun onResume() {
        super.onResume()
        dashboardBinding.spinnerDashboardFragment.setSelection(
            viewModel.preferenceManger
                .getSelection()
        )
    }

    @SuppressLint("SetTextI18n")
    private fun initUI(dashboardBinding: FragmentDashboardBinding) {
        // Error Handling getCategories
        viewModel.liveCategoriesFlow.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    (activity as PIBaseActivity).dismissProgressDialog("Category")
                }
                is NetworkResult.Error -> {
                    (activity as PIBaseActivity).dismissProgressDialog("Category")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    (activity as PIBaseActivity).showProgressDialog("Category")
                }
            }
        })

        // Error Handling get Data by category
        viewModel.liveDataFlow.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    (activity as PIBaseActivity).dismissProgressDialog("Data")
                }
                is NetworkResult.Error -> {
                    (activity as PIBaseActivity).dismissProgressDialog("Data")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    (activity as PIBaseActivity).showProgressDialog("Data")
                }
            }
        })

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
                // get category name
                category = listCategoriesName.first()
                adapter = adapters
                setSelection(0, false)
                gravity = Gravity.CENTER
                onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?, p1: View?, p2: Int, p3:
                        Long
                    ) {
                        viewModel.preferenceManger.setSelection(p2) // Save Selected Spinner Position
                        // Notify adapter and change data by categories
                        category = listCategoriesName[p2]
                        lifecycleScope.launch {
                            viewModel.getDataByCategory(listCategoriesName[p2]) //TODO
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
            mealAdapter = DashboardMealAdapter(context, this@DashboardFragment)
            adapter = mealAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

        viewModel.meals.observe(viewLifecycleOwner, Observer { item ->
            try {
                mealAdapter.setData(item.body()?.meals!!)
                elementNumber = mealAdapter.itemCount
                // set number of item and category name //TODO
                dashboardBinding.typesTextDashboardFragment.text =
                    "$elementNumber elements of $category"
                dashboardBinding.categoryTitleTextDashboardFragment.text = category
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.categories.removeObservers(viewLifecycleOwner)
        viewModel.meals.removeObservers(viewLifecycleOwner)
        viewModel.liveCategoriesFlow.removeObservers(viewLifecycleOwner)
        viewModel.liveDataFlow.removeObservers(viewLifecycleOwner)
    }

    override fun onItemClick(position: Int) {
        val args = position.toString() + TAG
        val action =
            BottomNavigationFragmentDirections.actionBottomNavigationFragmentToDetailsFragment(args)
        MainActivity.navController.navigate(action)
    }


}