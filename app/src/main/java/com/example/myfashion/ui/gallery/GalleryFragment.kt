package com.example.myfashion.ui.gallery

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myfashion.Image
import com.example.myfashion.ImageAdapter
import com.example.myfashion.databinding.FragmentGalleryBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class GalleryFragment : Fragment() {

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private var _binding: FragmentGalleryBinding? = null

    private lateinit var adapter: ImageAdapter

    var storageReference: StorageReference? = null

    private var allPictures: ArrayList<Image> = ArrayList()
    private var images: MutableList<Bitmap> = mutableListOf()
    interface ImagesLoadedCallback {
        fun onImagesLoaded(images: ArrayList<Image>)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false) // przekształcenia widoków zdefiniowanych w pliku XML układu na obiekty Kotlin
        val root: View = binding.root

        //    val textView: TextView = binding.textGallery
        //    galleryViewModel.text.observe(viewLifecycleOwner) {
        //      textView.text = it
        //    }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.getImage.setOnClickListener {
//
//        }
        uploadImage() ///////////////////////////////////////////////////////////////////////////////////////////////

        binding.imageRecycler?.layoutManager= GridLayoutManager(context, 3)
        binding.imageRecycler?.setHasFixedSize(true)
        allPictures = ArrayList()

        // Load images to all pictures
//        binding.progressBar?.visibility = View.VISIBLE
//        getAllImages(object: ImagesLoadedCallback {
//            override fun onImagesLoaded(images: ArrayList<Image>) {
//                // Set adapter to recycler
//                Log.e("to ja", images.toString())
//                binding.imageRecycler.adapter = context?.let { ImageAdapter(it, images) }
//                binding.progressBar?.visibility = View.GONE
//            }
//        })

        GlobalScope.launch(Dispatchers.IO) {
            fetchImagesFromFirebaseStorage()
        }

        // Load images to all pictures
        if(allPictures!!.isEmpty())
        {
            binding.progressBar?.visibility = View.VISIBLE
            Log.e("good", allPictures.toString())
            allPictures = getAllImages()
            Log.e("good", allPictures.toString())
            // Set adapter to recycler
            binding.imageRecycler.adapter = context?.let { ImageAdapter(it, allPictures!!) }
            binding.progressBar?.visibility = View.GONE
        }
        // getAllImages()

    }

    private suspend fun fetchImagesFromFirebaseStorage() {
        val storageRef = FirebaseStorage.getInstance().getReference()
        val imagesRef = storageRef.child("tshirts") // path to your images directory

        val listResult = imagesRef.listAll().await()

        listResult.items.forEach { imageRef ->
            val localFile = File.createTempFile("tempImage", "jpg")
            imageRef.getFile(localFile).await()

            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            images.add(bitmap)
        }
    }

    private fun getAllImages(): ArrayList<Image> { //callback: ImagesLoadedCallback
        val images: ArrayList<Image> = ArrayList()
//        var fileNamesTshirts: ArrayList<String>? = null
//        var fileNamesTrousers: ArrayList<String>? = null
        val tshirts: StorageReference = FirebaseStorage.getInstance().getReference().child("tshirts")
        val trousers: StorageReference = FirebaseStorage.getInstance().getReference().child("trousers")
        // sprobowac StorageDatabase, kopiuj link ze storage do realtime i zrob tam foldery
        tshirts.listAll()
            .addOnSuccessListener { listResult ->
                var index = 0
                for (item in listResult.items) {
                    item.downloadUrl.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            images.add(Image(task.result, "tshirt$index"))
                            //callback.onImagesLoaded(images)

                        }
                    }
                }
            }
            .addOnFailureListener {
                binding.getThisImage.setText("Failed to fetch t-shirts")
            }
//        tshirts.listAll()
//            .addOnSuccessListener { listResult ->
//                var index = 0
//                for (item in listResult.items) {
//                    item.downloadUrl.addOnSuccessListener {uri ->
//                       images.add(Image(uri, "tshirt$index"))
//                    }
//                }
//            }

//        trousers.listAll().addOnSuccessListener { listResult ->
//            for (item in listResult.items) {
//                val imagePath = item.downloadUrl.toString()
//                val imageName = item.name
//                val image = Image(imagePath, imageName)
//                images.add(image)
//            }
//            adapter.notifyDataSetChanged()
//        }.addOnFailureListener { binding.getThisImage.setText("Failed to fetch trousers") }
        Log.d("urichecking images", "$images")
        return images
    }

    private fun uploadImage() {
        val imageName = binding.getThisImage.text.toString()
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("trousers/image2.jpg")

        val localFile = File.createTempFile("tempImage", "jpg")
        binding.getThisImage.setText("tempfile")
        storageRef.getFile(localFile).
            addOnSuccessListener {

                // The image has been successfully downloaded
                // Display the image using the ImageView
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                binding.showImageHere.setImageBitmap(bitmap)
                binding.getThisImage.setText("ez")
            }.addOnFailureListener { binding.getThisImage.setText("fail") }
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