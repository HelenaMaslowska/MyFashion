package com.example.myfashion.ui.gallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfashion.GalleryImage
import com.example.myfashion.Image
import com.example.myfashion.ImageAdapter
import com.example.myfashion.ImageGalleryAdapter
import com.example.myfashion.MainViewModel
import com.example.myfashion.databinding.FragmentGalleryBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.internal.Sleeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class GalleryFragment : Fragment() {
    // realtime database
    private lateinit var viewModel: MainViewModel
    private val binding get() = _binding!!
    private var _binding: FragmentGalleryBinding? = null
    // inside class variables
    private var imageRecycler: RecyclerView?=null
    private var progressBar: ProgressBar?=null
    private var allImages: ArrayList<GalleryImage>? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        imageRecycler = binding.imageRecycler
        progressBar = binding.progressBar

        imageRecycler?.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecycler?.setHasFixedSize(true)

        allImages = ArrayList()
        if(allImages!!.isEmpty()) {
            progressBar?.visibility = View.VISIBLE
            // Get all images from storage
            allImages = getAllImages()
            // Set adapter to recycler
            imageRecycler?.adapter = ImageGalleryAdapter(requireContext(), allImages!!)
            progressBar?.visibility = View.GONE
        }

        return root
    }

    private fun getAllImages(): ArrayList<GalleryImage> {
        val images = ArrayList<GalleryImage>()
        for (img in viewModel.getAllTshirts()) {
            images.add(GalleryImage(img))
        }
        for(img in viewModel.getAllTrousers()) {
            images.add(GalleryImage(img))
        }
        return images
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
