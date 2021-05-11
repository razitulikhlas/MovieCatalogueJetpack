package com.razit.moviecatalogue


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.razit.moviecatalogue.adapter.SectionsPagerAdapter
import com.razit.moviecatalogue.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        supportActionBar?.hide()
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        activityHomeBinding.viewPager.adapter = sectionsPagerAdapter
        activityHomeBinding.tabs.setupWithViewPager(activityHomeBinding.viewPager)
        activityHomeBinding.viewPager.offscreenPageLimit = 0
        supportActionBar?.elevation = 0f
    }
}