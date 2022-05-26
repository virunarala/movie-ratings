package com.example.moviesapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.batofgotham.moviereviews.ui.fragment.home.MovieFragment
import com.batofgotham.moviereviews.ui.fragment.home.TvFragment

//view Pager Adapter to do swipe fragments in main activity

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getPageTitle(position: Int): String {
        if (position == 0)
            return "Movies"
        else if (position == 1)
            return "Tv"
        return "Movies"
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return MovieFragment()
        else if (position == 1)
            return TvFragment()
        return MovieFragment()
    }
}