package com.app.covidhelp.init

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.covidhelp.activities.DashBoardActivity
import com.app.covidhelp.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class AppRoutingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseAuth.getInstance().addAuthStateListener {
            if (it.currentUser == null) {

                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                            Arrays.asList(
                                AuthUI.IdpConfig.PhoneBuilder().build()
                            )
                        )
                        .build(), 125
                )
            } else {
                launch()
            }
        }

    }

    fun launch() {
        val intent = Intent(this, DashBoardActivity::class.java)
        startActivity(intent)
        finish()
    }
}