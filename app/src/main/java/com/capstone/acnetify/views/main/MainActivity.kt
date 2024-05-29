package com.capstone.acnetify.views.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.acnetify.R
import com.capstone.acnetify.databinding.ActivityMainBinding
import com.capstone.acnetify.views.acne_types.AcneTypesFragment
import com.capstone.acnetify.views.history_acne.HistoryAcneFragment
import com.capstone.acnetify.views.home.HomeFragment
import com.capstone.acnetify.views.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null

        // Set the initial fragment
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        @Suppress("DEPRECATION")
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.acneTypes -> {
                    replaceFragment(AcneTypesFragment())
                    true
                }
                R.id.history -> {
                    replaceFragment(HistoryAcneFragment())
                    true
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

        binding.fab.setOnClickListener {
            // Handle FAB click, e.g., open camera or navigate to a specific fragment
            // For example, open a new fragment or activity
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}