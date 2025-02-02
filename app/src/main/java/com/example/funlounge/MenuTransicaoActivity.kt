package com.example.funlounge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MenuTransicaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_transicao)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager2)
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)

        // Set up the adapter
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Link TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Menu Principal"
                1 -> "Acerca de"
                else -> "Tab $position"
            }
        }.attach()
    }
}