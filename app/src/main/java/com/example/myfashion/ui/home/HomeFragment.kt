package com.example.myfashion.ui.home

import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        uploadBasicImages() // async

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.ProgressBar1.visibility = View.GONE
        showGif()

        binding.firstNext.setOnClickListener {
            val currTshirts = viewModel.getCurrentTshirt().toString().toIntOrNull() //0
            if (currTshirts != null) {
                updateCurrentImage("tshirts", ((currTshirts + 1) % 3).toString())
            }
        }
        binding.secondNext.setOnClickListener {
            val currTrousers = viewModel.getCurrentTrousers().toString().toIntOrNull()
            if (currTrousers != null) {
                updateCurrentImage("trousers", ((currTrousers + 1) % 3).toString())
            }
        }
        binding.firstBack.setOnClickListener {
            val currTshirts = viewModel.getCurrentTshirt().toString().toIntOrNull() //0
            if (currTshirts != null) {
                if (currTshirts - 1 >= 0) {
                    updateCurrentImage("tshirts", ((currTshirts - 1) % 3).toString())
                }
                else {
                    updateCurrentImage("tshirts", "2")
                }
            }
        }
        binding.secondBack.setOnClickListener {
            val currTrousers = viewModel.getCurrentTrousers().toString().toIntOrNull()
            if (currTrousers != null) {
                if (currTrousers - 1 >= 0) {
                    updateCurrentImage("trousers", ((currTrousers - 1) % 3).toString())
                }
                else {
                    updateCurrentImage("trousers", "2")
                }
            }
        }

        binding.saveOutfit.setOnClickListener {
            val progress = binding.ProgressBar1
            progress.visibility = View.VISIBLE
            val img1 = viewModel.getCurrentTshirt()
            val img2 = viewModel.getCurrentTrousers()
            val outfits = viewModel.getOutfitsList().value
            val outfitsTitles = viewModel.getOutfitsTitles().value
            outfits?.add(Pair(img1, img2))
            outfitsTitles?.add(binding.setTitleOfOutfit.text.toString())
            if (outfits != null && outfitsTitles != null) {
                viewModel.setOutfitsList(outfits)
                viewModel.setOutfitsTitles(outfitsTitles)
            }
            Toast.makeText(requireContext(), "Outfit ${binding.setTitleOfOutfit.text} saved!", Toast.LENGTH_SHORT).show()
            progress.visibility = View.GONE
        }
    }

    private fun uploadBasicImages () {
        binding.ProgressBar1.visibility = View.VISIBLE
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
        binding.ProgressBar1.visibility = View.GONE
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
    fun showGif() {
        val image1: ImageView = binding.imageGif
        val image2: ImageView = binding.imageGif2
        Glide.with(this).load(R.drawable.arrow).into(image1)
        Glide.with(this).load(R.drawable.arrow2).into(image2)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
