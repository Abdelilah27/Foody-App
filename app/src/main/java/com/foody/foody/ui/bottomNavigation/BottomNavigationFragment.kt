package com.foody.foody.ui.bottomNavigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.foody.foody.R
import com.foody.foody.databinding.FragmentBottomNavigationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationFragment : Fragment(R.layout.fragment_bottom_navigation) {
    companion object {
        lateinit var navController: NavController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBottomNavigationBinding.bind(view)


        // Bottom Navigation Implementation
        setHasOptionsMenu(true)
        val navHost = childFragmentManager.findFragmentById(R.id.nav_host_frame_layout) as
                NavHostFragment
        navController = navHost.findNavController()

        view.findViewById<BottomNavigationView>(R.id.BottomNavigationView)
            .setupWithNavController(navController)

    }
}