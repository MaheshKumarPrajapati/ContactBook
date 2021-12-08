package com.example.contactbook.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.contactbook.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var fragmentContainerView : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationBarView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        fragmentContainerView = supportFragmentManager.findFragmentById(R.id.fragmentMainContainerView) as NavHostFragment
        val navigationController = fragmentContainerView.navController
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.personalContactFragment,
            R.id.allContactsFragment,
            R.id.businessContactsFragment
        ))
        setupActionBarWithNavController(navigationController,appBarConfig)
        bottomNavigationBarView.setupWithNavController(navigationController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return fragmentContainerView.navController.navigateUp() || super.onSupportNavigateUp()
    }

}