package com.erwindevtech.wisatareligi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erwindevtech.wisatareligi.Activity.AboutActivity
import com.erwindevtech.wisatareligi.R
import kotlinx.android.synthetic.main.activity_more.*

class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        supportActionBar!!.title = "More"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        more.setOnClickListener {
            val intentAbout = Intent(this@MoreActivity,AboutActivity::class.java )
            startActivity(intentAbout)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}