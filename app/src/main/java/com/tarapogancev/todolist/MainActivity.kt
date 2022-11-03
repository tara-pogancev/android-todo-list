package com.tarapogancev.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.tarapogancev.todolist.navigation.Navigation

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = Navigation(navHostFragment.navController)

        appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph)
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navController.navigateUp() || super.onSupportNavigateUp()
    }
}