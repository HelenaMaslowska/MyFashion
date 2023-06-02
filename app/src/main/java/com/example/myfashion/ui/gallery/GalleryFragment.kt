package com.example.myfashion.ui.gallery

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfashion.databinding.FragmentGalleryBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class GalleryFragment : Fragment() {



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var _binding: FragmentGalleryBinding? = null
    lateinit var ImageUri: Uri
    var storageReference: StorageReference? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //    val textView: TextView = binding.textGallery
        //    galleryViewModel.text.observe(viewLifecycleOwner) {
        //      textView.text = it
        //    }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getImage.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        val imageName = binding.getThisImage.text.toString()
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("trousers/flower.jpg")

        val localFile = File.createTempFile("tempImage", "jpg")
        binding.getThisImage.setText("tempfile")
        storageRef.getFile(localFile).
            addOnSuccessListener {

                // The image has been successfully downloaded
                // Display the image using the ImageView
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                binding.showImageHere.setImageBitmap(bitmap)
                binding.getThisImage.setText("ez")
            }.addOnFailureListener {
                binding.getThisImage.setText("fail")
            }
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val storage = FirebaseStorage.getInstance()
//        val storageRef: StorageReference = storage.reference.child("tshirts/image0.jpg")
//
//        val localFile = File.createTempFile("image0", "jpg")
//        storageRef.getFile(localFile)
//            .addOnSuccessListener {
//                // The image has been successfully downloaded
//                // Display the image using the ImageView
//                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//                showImageHere.setImageBitmap(bitmap)
//            }
//            .addOnFailureListener {
//                // Handle any errors that occurred during the download
//            }
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}