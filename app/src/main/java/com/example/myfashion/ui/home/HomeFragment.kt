package com.example.myfashion.ui.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfashion.R
import com.example.myfashion.databinding.FragmentHomeBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View
    {
        // VIEW SET UP ###########################################################################
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //    val textView: TextView = binding.textHome
        //    homeViewModel.text.observe(viewLifecycleOwner) { textView.text = it }
        // VIEW SET UP ###########################################################################
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uploadBasicImages() // async
    }

    fun uploadBasicImages () {
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("tshirts/image3.jpg")

        val localFile = File.createTempFile("tempImage", "jpg")

        storageRef.getFile(localFile).
        addOnSuccessListener {
            // The image has been successfully downloaded. Display the image using the ImageView
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.firstImage.setImageBitmap(bitmap)

        }.addOnFailureListener { binding.firstImage.setImageResource(R.drawable.ic_home_black_24dp) }

        val storageRef2 = FirebaseStorage.getInstance().reference.child("trousers/image2.jpg")
        val localFile2 = File.createTempFile("tempImage", "jpg")
        storageRef2.getFile(localFile2).
        addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile2.absolutePath)
            binding.secondImage.setImageBitmap(bitmap)
        }.addOnFailureListener { binding.secondImage.setImageResource(R.drawable.ic_home_black_24dp) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}