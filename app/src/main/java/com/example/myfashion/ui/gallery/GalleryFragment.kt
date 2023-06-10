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
    private lateinit var viewModel: MainViewModel
    private val binding get() = _binding!!
    private var _binding: FragmentGalleryBinding? = null
    // inside class variables
    private var imageRecycler: RecyclerView?=null
    private var progressBar: ProgressBar?=null
    private var allImages: ArrayList<GalleryImage>? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        _binding = FragmentGalleryBinding.inflate(inflater, container, false) // przekształcenia widoków zdefiniowanych w pliku XML układu na obiekty Kotlin, od tego momentu mamy dostęp do xml
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
        for (img in viewModel.getAllTshirts())
        {
            images.add(GalleryImage(img))
        }
        for(img in viewModel.getAllTrousers())
        {
            images.add(GalleryImage(img))
        }
        return images
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
//    val textView: TextView = binding.textGallery
//    galleryViewModel.text.observe(viewLifecycleOwner) { textView.text = it }


// wersje dodawania obrazka /////////////////////////////////////////////////////////////////////
//        val gson = Gson()
//        Log.d("odbieram jsona i deserializuje go", "$tshirtsList")
//        val listJson = arguments?.getString("myList")
//tshirtsList = gson.fromJson(listJson, object: TypeToken<ArrayList<Image>>() {}.type)

//        val bundle = arguments
//        val listJson = bundle?.getString("myList")
//
//        if (listJson != null) {
//            Log.d("niby odebral!", "$listJson")
//            val gson = Gson()
//            val type = object : TypeToken<ArrayList<Image>>() {}.type
//            list = gson.fromJson(listJson, type)
//            // teraz możesz używać listy
//        }
//        val bundle = arguments
//        val message = arguments?.getString("myList")
//        val bundle = intent.extras
//        var tempString: String = ""
//        s = bundle!!.getString("key1", tempString))
//        binding.getImage.setOnClickListener() {
//            var tempString: ArrayList<Image> = mainVM.tshirtList.observe()
//            Log.d("odebrane!", "$tempString")
//
//            if(allPictures!!.isEmpty())
//            {
//                binding.progressBar?.visibility = View.VISIBLE
//                allPictures = tempString
//                Log.e("good", allPictures.toString())
//                // Set adapter to recycler
//                binding.imageRecycler.adapter = context?.let { ImageAdapter(it, allPictures!!) }
//                binding.progressBar?.visibility = View.GONE
//            }
//        }



// pierwsze działające dodawanie obrazka z firebase storage ////////////////////////////////////////
//fun newInstance(someInt: Int): GalleryFragment? {
//    val myFragment = GalleryFragment()
//    val args = Bundle()
//    args.putInt("someInt", someInt)
//    myFragment.setArguments(args)
//    return myFragment
//}
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//    binding.getImage.setOnClickListener {
//    }
//    uploadImage() ///////////////////////////////////////////////////////////////////////////////////////////////
//
//    binding.imageRecycler?.layoutManager= GridLayoutManager(context, 3)
//    binding.imageRecycler?.setHasFixedSize(true)
//    allPictures = ArrayList()
//}
//
//private fun uploadImage() {
//    val imageName = binding.getThisImage.text.toString()
//    val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("trousers/image2.jpg")
//
//    val localFile = File.createTempFile("tempImage", "jpg")
//    binding.getThisImage.setText("tempfile")
//    storageRef.getFile(localFile).
//    addOnSuccessListener {
//
//        // The image has been successfully downloaded
//        // Display the image using the ImageView
//        val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//        binding.showImageHere.setImageBitmap(bitmap)
//        binding.getThisImage.setText("ez")
//    }.addOnFailureListener { binding.getThisImage.setText("fail") }
//}


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

//        GlobalScope.launch(Dispatchers.IO) {
//            fetchImagesFromFirebaseStorage()
//        }

// Load images to all pictures

// getAllImages()

//    private suspend fun fetchImagesFromFirebaseStorage() {
//        val storageRef = FirebaseStorage.getInstance().getReference()
//        val imagesRef = storageRef.child("tshirts") // path to your images directory
//
//        val listResult = imagesRef.listAll().await()
//
//        listResult.items.forEach { imageRef ->
//            val localFile = File.createTempFile("tempImage", "jpg")
//            imageRef.getFile(localFile).await()
//
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            images.add(bitmap)
//        }
//    }

//private fun getAllImages(): ArrayList<Image> { //callback: ImagesLoadedCallback
//    val images1: ArrayList<Image> = ArrayList()
////        var fileNamesTshirts: ArrayList<String>? = null
////        var fileNamesTrousers: ArrayList<String>? = null
//    val tshirts: StorageReference = FirebaseStorage.getInstance().getReference().child("tshirts")
//    val trousers: StorageReference = FirebaseStorage.getInstance().getReference().child("trousers")
//    // sprobowac StorageDatabase, kopiuj link ze storage do realtime i zrob tam foldery
////        tshirts.listAll()
////            .addOnSuccessListener { listResult ->
////                var index = 0
////                for (item in listResult.items) {
////                    item.downloadUrl.addOnCompleteListener { task ->
////                        if (task.isSuccessful) {
////                            images.add(Image(task.result, "tshirt$index"))
////                            //callback.onImagesLoaded(images)
////
////                        }
////                    }
////                }
////            }
//
