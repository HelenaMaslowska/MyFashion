package com.example.myfashion.ui.home

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
import com.example.myfashion.R
import com.example.myfashion.databinding.FragmentHomeBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class HomeFragment : Fragment() {
private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //    val textView: TextView = binding.textHome
        //    homeViewModel.text.observe(viewLifecycleOwner) { textView.text = it }
        uploadBasicImages() // async
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
//        viewModel.getTshirts("image1").observe(viewLifecycleOwner) { img ->
//
//        }
        binding.firstNext.setOnClickListener {
            val currTshirts = viewModel.getCurrentTshirt().toString().toIntOrNull() //0
            if (currTshirts != null) {
                updateCurrentImage("tshirts", ((currTshirts + 1) % 3).toString())
            }

        }
        binding.secondNext.setOnClickListener {
            val currTrousers = viewModel.getCurrentTrousers().toString().toIntOrNull()
            if (currTrousers != null) {
                    Log.d("haha nie działa tro", viewModel.getCurrentTrousers().toString())
                    Log.d("zmieniam na tro", ((currTrousers + 1) % 3).toString())
                    updateCurrentImage("trousers", ((currTrousers + 1) % 3).toString())
            }
        }
        binding.firstBack.setOnClickListener {
            val currTshirts = viewModel.getCurrentTshirt().toString().toIntOrNull() //0
            if (currTshirts != null) {
                if (currTshirts - 1 >= 0) {
                    updateCurrentImage("tshirts", ((currTshirts - 1) % 3).toString())
                }
                else
                {
                    updateCurrentImage("tshirts", "2")
                }
            }
        }
        binding.secondBack.setOnClickListener {
            val currTrousers = viewModel.getCurrentTrousers().toString().toIntOrNull()
            if (currTrousers != null) {
                if (currTrousers - 1 >= 0) {
                    Log.d("haha nie działa trou", viewModel.getCurrentTrousers().toString())
                    Log.d("zmieniam na tro", ((currTrousers - 1) % 3).toString())
                    updateCurrentImage("trousers", ((currTrousers - 1) % 3).toString())
                }
                else
                {
                    updateCurrentImage("trousers", "2")
                }
            }
        }

        binding.saveOutfit.setOnClickListener {
            Log.d("tshirts", viewModel.getCurrentTshirt().toString())
            Log.d("trousers", viewModel.getCurrentTrousers().toString())
            Log.d("tr/image0", viewModel.getTrousers("image0").toString())
            Log.d("tr/image1", viewModel.getTrousers("image1").toString())
            Log.d("tr/image2", viewModel.getTrousers("image2").toString())
        }
    }

    private fun uploadBasicImages () {
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("tshirts/image0.jpg")
        val localFile = File.createTempFile("tempImage", "jpg")

        storageRef.getFile(localFile).
        addOnSuccessListener {
            // The image has been successfully downloaded. Display the image using the ImageView
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.firstImage.setImageBitmap(bitmap)
            viewModel.setCurrentTshirt("image0")
        }.addOnFailureListener { binding.firstImage.setImageResource(R.drawable.ic_home_black_24dp) }

        val storageRef2 = FirebaseStorage.getInstance().reference.child("trousers/image0.jpg")
        val localFile2 = File.createTempFile("tempImage", "jpg")
        storageRef2.getFile(localFile2).
        addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile2.absolutePath)
            binding.secondImage.setImageBitmap(bitmap)
            viewModel.setCurrentTrousers("image0")
        }.addOnFailureListener { binding.secondImage.setImageResource(R.drawable.ic_home_black_24dp) }
    }

    private fun updateCurrentImage(cloth:String, num: String) {

        if (cloth == "tshirts") {
            val image = viewModel.getTshirts("image$num")
            binding.firstImage.setImageBitmap(image.value)
            viewModel.setCurrentTshirt("image$num")
        }
        else
        {
            val image = viewModel.getTrousers("image$num")
            binding.secondImage.setImageBitmap(image.value)
            viewModel.setCurrentTrousers("image$num")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}