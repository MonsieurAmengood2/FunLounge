package com.example.funlounge

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.funlounge.fragmentos.AboutFragment
import com.example.funlounge.fragmentos.MenuTransicaoFragment

class ViewPagerAdapter(activity: MenuTransicaoActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return MenuTransicaoFragment()
            1 -> return AboutFragment()
            else -> return MenuTransicaoFragment()
        }
    }
}
