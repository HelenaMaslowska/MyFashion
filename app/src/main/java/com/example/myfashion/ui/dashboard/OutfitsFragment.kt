package com.example.myfashion.ui.outfits

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfashion.MainViewModel
import com.example.myfashion.databinding.FragmentDashboardBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class OutfitsFragment : Fragment() {

private var _binding: FragmentDashboardBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
    val outfitsViewModel = ViewModelProvider(this).get(OutfitsViewModel::class.java)
    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textDashboard
    outfitsViewModel.text.observe(viewLifecycleOwner) {
        textView.text = it
    }

    viewModel.outfits.observe(viewLifecycleOwner) {
        for(outfit in it) {
            val img1 = viewModel.getTshirts("image${outfit.first}").value
            val img2 = viewModel.getTshirts("image${outfit.second}").value

        }
    }
    return root
}

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}