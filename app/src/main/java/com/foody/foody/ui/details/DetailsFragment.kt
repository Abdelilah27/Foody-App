package com.foody.foody.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.foody.foody.MainActivity
import com.foody.foody.R
import com.foody.foody.databinding.FragmentDetailsBinding
import com.foody.foody.model.MealX
import com.foody.foody.utils.ConstUtil.strIngredient
import com.foody.foody.utils.NetworkResult
import com.foody.foody.utils.PIBaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.full.memberProperties

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var detailsBinding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        initUI(detailsBinding)
        return detailsBinding.root
    }

    private fun initUI(detailsBinding: FragmentDetailsBinding) {
        // Error Handling getCategories
        viewModel.liveDataFlow.observe(viewLifecycleOwner, Observer {
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

        viewModel.mealsDetails.observe(viewLifecycleOwner, Observer {
            val meal = it.body()!!.meals.first()
            detailsBinding.titleItemRecyclerFavoriteFragment.text = meal.strMeal
            detailsBinding.categoryTitleDetailsFragment.text = meal.strCategory
            detailsBinding.countryTitleDetailsFragment.text = meal.strArea
            detailsBinding.instructionsDetailsFragment.text = meal.strInstructions
            // For display all ingredient in the same TextView
            var ingredient = ""
            for (prop in MealX::class.memberProperties) {
                if (prop.name.contains(strIngredient)) {
                    if (prop.get(meal).toString().isNotEmpty() && prop.get(meal).toString() != "null") {
                        ingredient += prop.get(meal).toString() + "\n"
                    }
                }
            }
            detailsBinding.ingredientDetailsFragment.text = ingredient
        })


        // on Back button click
        detailsBinding.back.setOnClickListener {
            MainActivity.navController.navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.liveDataFlow.removeObservers(viewLifecycleOwner)
        viewModel.mealsDetails.removeObservers(viewLifecycleOwner)
    }
}