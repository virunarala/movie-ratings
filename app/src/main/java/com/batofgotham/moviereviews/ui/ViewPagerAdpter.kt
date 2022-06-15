package com.batofgotham.moviereviews.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.batofgotham.moviereviews.ui.movie.MovieFragment
import com.batofgotham.moviereviews.ui.tvshow.TvFragment

//view Pager Adapter to do swipe fragments in main activity

private const val NUM_TABS = 2

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MovieFragment()
            1 -> return TvFragment()
        }
        return MovieFragment()
    }

}