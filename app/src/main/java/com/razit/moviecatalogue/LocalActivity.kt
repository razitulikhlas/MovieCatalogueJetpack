package com.razit.moviecatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.razit.moviecatalogue.adapter.SectionsPagerAdapter
import com.razit.moviecatalogue.databinding.ActivityLocalActivityBinding
import com.razit.moviecatalogue.fragment.FilmFragment
import com.razit.moviecatalogue.fragment.LocalFragment

class LocalActivity : AppCompatActivity() {

    private val listFragmentLocal = arrayListOf(
        LocalFragment.newInstance(BuildConfig.MOVIES),
        LocalFragment.newInstance(BuildConfig.TVSHOW)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityLocal = ActivityLocalActivityBinding.inflate(layoutInflater)
        setContentView(activityLocal.root)
        supportActionBar?.hide()
        val sectionsPagerAdapter =
            SectionsPagerAdapter(lifecycle, supportFragmentManager, listFragmentLocal)
        activityLocal.viewPagerLocal.adapter = sectionsPagerAdapter
        activityLocal.tabs.addTab(activityLocal.tabs.newTab().setText(R.string.movies))
        activityLocal.tabs.addTab(activityLocal.tabs.newTab().setText(R.string.tvShow))
        activityLocal.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let {
                    activityLocal.viewPagerLocal.currentItem = it
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        activityLocal.viewPagerLocal.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                activityLocal.tabs.selectTab(activityLocal.tabs.getTabAt(position))
            }
        })
        supportActionBar?.elevation = 0f
    }

}