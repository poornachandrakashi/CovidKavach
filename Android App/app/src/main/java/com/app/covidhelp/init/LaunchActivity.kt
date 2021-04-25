package com.app.covidhelp.init

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.covidhelp.AppPreference

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AppPreference.getInstance(this).isFirstTimeLaunch()) {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }
    }
}