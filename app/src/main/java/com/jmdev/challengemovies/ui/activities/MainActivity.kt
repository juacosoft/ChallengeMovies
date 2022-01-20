package com.jmdev.challengemovies.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import challengemovies.BuildConfig
import challengemovies.R
import challengemovies.databinding.ActivityMainBinding


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView)as NavHostFragment
        navController=navHostFragment.navController
        binding.bottomNavBar.setupWithNavController(navController)
        Log .d("MainActivity","Release ${BuildConfig.COMPILE_MODE}")
        navController.addOnDestinationChangedListener{_,destination,_->
            when(destination.id){
                R.id.detailMovieFragment->{
                    binding.bottomNavBar.visibility=View.GONE
                }
                R.id.videoPlayerFragment->{
                    binding.bottomNavBar.visibility=View.GONE
                }
                R.id.seeMoreMovieFragment->{
                    binding.bottomNavBar.visibility=View.GONE
                }
                else -> binding.bottomNavBar.visibility=View.VISIBLE
            }
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        onCreate(savedInstanceState)
    }
}