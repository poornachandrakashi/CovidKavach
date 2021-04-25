package com.app.covidhelp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.app.covidhelp.R
import com.app.covidhelp.databinding.ActivityDashBoardBinding

class DashBoardActivity : AppCompatActivity() {

    lateinit var bind: ActivityDashBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val navController = Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(bind.bottomNavigationView, navController)

        bind.floatingActionButton.setOnClickListener {

            navController.navigate(R.id.addFeedActivity)
        }

    }
}