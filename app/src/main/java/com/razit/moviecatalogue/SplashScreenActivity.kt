package com.razit.moviecatalogue

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.razit.moviecatalogue.utils.Constanta.DELAY_SPLASH

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }, DELAY_SPLASH)

    }
}