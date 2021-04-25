package com.app.covidhelp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.covidhelp.GenericFeed
import com.app.covidhelp.R
import com.app.covidhelp.databinding.ActivityAddFeedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shivtechs.maplocationpicker.LocationPickerActivity
import com.shivtechs.maplocationpicker.MapUtility
import java.util.*


class AddFeedActivity : AppCompatActivity() {
    val ADDRESS_PICKER_REQUEST = 256;
    lateinit var bind: ActivityAddFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityAddFeedBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.submit.setOnClickListener {
            add()
        }
        bind.pickloc.setOnClickListener {
            pickLoc()
        }


    }

    private fun pickLoc() {
        MapUtility.apiKey = getResources().getString(R.string.your_api_key);

        val i = Intent(this@AddFeedActivity, LocationPickerActivity::class.java)
        startActivityForResult(i, ADDRESS_PICKER_REQUEST)
    }

    var mlat: Double = 0.0;
    var mlon: Double = 0.0
    fun add() {
        bind.submit.isEnabled = false
        var feed = GenericFeed()
        feed.apply {
            userID = FirebaseAuth.getInstance().currentUser?.phoneNumber!!
            id = UUID.randomUUID().toString()
            contact = bind.contact.text.toString()
            description = bind.description.text.toString()
            trust = 0
            this.lat = mlat
            this.lon = mlon

            bind.tags.text.toString().split(",").forEach(
                {
                    tags.add(it)
                }

            )


        }

        FirebaseFirestore.getInstance().collection("feeds")
            .document(feed.id).set(feed).addOnSuccessListener {
                finish()
            }
            .addOnFailureListener {
                bind.submit.isEnabled = true
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADDRESS_PICKER_REQUEST) {
            mlat = data?.extras?.getDouble(MapUtility.LATITUDE, 0.0)!!
            mlon = data?.extras?.getDouble(MapUtility.LONGITUDE, 0.0)!!

            Log.d("TAG", "onActivityResult:$mlat $mlon ")

        }
    }
}