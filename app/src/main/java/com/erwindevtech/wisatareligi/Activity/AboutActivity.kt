package com.erwindevtech.wisatareligi.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erwindevtech.wisatareligi.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar!!.title = "About Wisata Religi"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
