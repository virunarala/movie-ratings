package com.batofgotham.moviereviews.ui.bottomsheetnav

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.batofgotham.moviereviews.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        onSettingOptionsClick()

        return view
    }

    private fun onSettingOptionsClick() {
        binding.llLanguage.setOnClickListener {
            changeLanguage()
        }
        binding.llTheme.setOnClickListener {
            changeTheme()
        }
        binding.llDelete.setOnClickListener {
            deleteAll()
        }
        binding.llShareApk.setOnClickListener {
            shareApk()
        }
        binding.llContactUs.setOnClickListener {
            contactUs()
        }
    }

    private fun changeLanguage() {
        val languages = arrayOf("Hindi", "English")
        val mBuilder = AlertDialog.Builder(context)
            .setTitle("Choose Language")
            .setSingleChoiceItems(languages, -1, DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            }).create().show()
    }

    private fun changeTheme() {

    }

    private fun deleteAll() {

    }

    private fun shareApk() {

    }

    private fun contactUs() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}