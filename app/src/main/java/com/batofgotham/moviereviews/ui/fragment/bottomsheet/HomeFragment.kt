package com.batofgotham.moviereviews.ui.fragment.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.batofgotham.moviereviews.databinding.FragmentHomeBinding
import com.example.moviesapp.ui.adapter.ViewPagerAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val pagerAdapter = ViewPagerAdapter(childFragmentManager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}