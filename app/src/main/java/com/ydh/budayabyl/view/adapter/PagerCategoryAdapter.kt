package com.ydh.budayabyl.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ydh.budayabyl.view.ListCategoryFragment

class PagerCategoryAdapter(
    private val list: Array<String>,
    fragmentManager: FragmentManager
): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getPageTitle(position: Int): CharSequence? {
        return list[position]
    }

    override fun getCount(): Int {
        return  list.size
    }

    override fun getItem(position: Int): Fragment {
        return ListCategoryFragment().onSavedInstanceFragment(list[position])
    }
}