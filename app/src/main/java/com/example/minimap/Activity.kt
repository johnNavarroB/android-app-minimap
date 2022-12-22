package com.example.minimap

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView


class Activity : AppCompatActivity()
{
    lateinit var navHostFragment : NavHostFragment
    lateinit var navController : NavController
    lateinit var appBarConfiguration : AppBarConfiguration
    lateinit var drawerLayout : DrawerLayout
    lateinit var navigationView : NavigationView


    override fun onSupportNavigateUp() : Boolean
    {
        navHostFragment = supportFragmentManager.findFragmentById( R.id.hostContainerView ) as NavHostFragment
        navController = navHostFragment.navController
        return navController.navigateUp( appBarConfiguration ) || super.onSupportNavigateUp()
    }


    override fun onCreate( savedInstanceState : Bundle? )
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity )

        navHostFragment = supportFragmentManager.findFragmentById( R.id.hostContainerView ) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = findViewById( R.id.drawerLayout )
        navigationView = findViewById( R.id.navigationView )

        val navigationHeader = navigationView.getHeaderView( 0 )
        (navigationHeader.findViewById( R.id.headerImage ) as ImageView).setImageResource( R.drawable.camera )

        navigationView.setupWithNavController( navController )
        appBarConfiguration = AppBarConfiguration( navController.graph, drawerLayout )

        //setupWithNavController( navController, appBarConfiguration )
    }
}