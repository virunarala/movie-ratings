package com.batofgotham.moviereviews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batofgotham.moviereviews.databinding.FragmentChoiceChipsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChoiceChipsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentChoiceChipsBinding? = null

    private val binding: FragmentChoiceChipsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChoiceChipsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}