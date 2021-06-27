package com.razit.moviecatalogue


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.razit.moviecatalogue.adapter.SectionsPagerAdapter
import com.razit.moviecatalogue.databinding.ActivityHomeBinding
import com.razit.moviecatalogue.fragment.FilmFragment

class HomeActivity : AppCompatActivity() {

    private val listFragment = arrayListOf(
        FilmFragment.newInstance(BuildConfig.MOVIES),FilmFragment.newInstance(BuildConfig.TVSHOW)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        supportActionBar?.hide()
        val sectionsPagerAdapter = SectionsPagerAdapter(lifecycle, supportFragmentManager,listFragment)
        activityHomeBinding.viewPager.adapter = sectionsPagerAdapter
        activityHomeBinding.tabs.addTab(activityHomeBinding.tabs.newTab().setText(R.string.movies))
        activityHomeBinding.tabs.addTab(activityHomeBinding.tabs.newTab().setText(R.string.tvShow))
        activityHomeBinding.ivSetting.setOnClickListener {
            startActivity(Intent(this,LocalActivity::class.java))
        }
        activityHomeBinding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    activityHomeBinding.viewPager.currentItem = it
                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        activityHomeBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                activityHomeBinding.tabs.selectTab(activityHomeBinding.tabs.getTabAt(position))
            }
        })
        supportActionBar?.elevation = 0f
    }
}