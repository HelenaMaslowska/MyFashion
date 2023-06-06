package com.example.myfashion


import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfashion.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import java.lang.Exception
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import com.example.myfashion.ui.gallery.GalleryFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay


import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
class MainActivity : AppCompatActivity() {

    private val images: MutableList<Bitmap> = mutableListOf()
    private lateinit var tshirtsList: ArrayList<Image>
    private lateinit var binding: ActivityMainBinding
    private lateinit var progress: ProgressBar

    private var loading = 0
    //private var imageRecycler: RecyclerView?=null
    //private var progressBar: ProgressBar?=null
    //private var allPictures: ArrayList<Image>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ***************** ***************** ***************** *****************
        // ***************** FIREBASE SETUP
        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory( PlayIntegrityAppCheckProviderFactory.getInstance() )
        // ***************** *****************  ***************** *****************
        // ***************** NAV SETUP
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_outfits, R.id.navigation_gallery
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // ***************** *****************  ***************** *****************
        //fetchImagesFromFirebaseStorage()
        progress = findViewById(R.id.progressBar)
        progress.visibility = View.GONE


        CoroutineScope(IO).launch {
            addImages()

//            bundle.putString("myList", tshirtsList.toString())
//            //bundle.putString("myList", listJson)
//            val intent = Intent(this@MainActivity, GalleryFragment::class.java)
//            intent.putExtras(bundle)
            val fragment = GalleryFragment()
            val bundle = Bundle()
            Log.d ("a co ja wysylam?", tshirtsList.toString())
            bundle.putString("String", tshirtsList.toString())
            fragment.arguments = bundle
            supportFragmentManager.beginTransaction()  // 'fragment_container' to ID Twojego kontenera na fragmenty
                .commit()

            //fragment.arguments = bundle
            //fragment.arguments = bundle
            //Log.d("argumenty", "${fragment.arguments}")
            //supportFragmentManager.beginTransaction().commit()
        }

    }

    private suspend fun addImages() {
        val tshirts: StorageReference = FirebaseStorage.getInstance().getReference().child("tshirts")
        var tshirtsL: ArrayList<Image> = ArrayList()
        setProgress1(1)

        tshirts.listAll()
        .addOnSuccessListener {listResult ->
            var index = 0
            for (item in listResult.items) {
                item.downloadUrl.addOnCompleteListener { uri ->
                    if (uri.isSuccessful) {

                        tshirtsL = (tshirtsL + Image(uri.result, "tshirt$index")) as ArrayList<Image>
                        //Log.d("urichecking", "$tshirtsL")
                    }
                    index += 1
                    Log.d("zakonczone", "$tshirtsL")
                }
            }
        }
        .addOnFailureListener {
            Log.e("oh noo error", images.toString())
        }.await()
        delay(2000)    // delay czeka na asynchroniczny wątek ten listAll
        setProgress1(0)
        setTshirtsListToMainThread(tshirtsL)
    }

    private suspend fun setProgress1(loading: Int)
    {
        withContext(Main) {
            setProgressVisible(loading)
        }
    }
    private fun setProgressVisible(loading: Int) {
        if (loading == 1) progress.visibility = View.VISIBLE
        else progress.visibility = View.GONE
    }
    private suspend fun setTshirtsListToMainThread(tshirtsL: ArrayList<Image>) {
        withContext(Main) {
            tshirtsList = tshirtsL
            Log.d("tshirts", "$tshirtsList")
        }
    }

    private fun fetchImagesFromFirebaseStorage() {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("tshirts") // ścieżka do Twojego katalogu ze zdjęciami

        imagesRef.listAll().addOnSuccessListener { listResult ->
            listResult.items.forEach { imageRef ->
                val localFile = File.createTempFile("tempImage", "jpg")

                imageRef.getFile(localFile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    images.add(bitmap)
                }.addOnFailureListener {
                    Log.e("oh noo error", images.toString())
                }
            }
        }.addOnFailureListener {
            Log.e("oh noo error", images.toString())
        }
    }

        //val tshirts: StorageReference = FirebaseStorage.getInstance().getReference().child("tshirts")
        //val trousers: StorageReference = FirebaseStorage.getInstance().getReference().child("trousers")
//        var index = 0
//        tshirts.listAll()
//            .addOnSuccessListener { listResult ->
//
//                for (item in listResult.items) {
//                    item.downloadUrl.addOnCompleteListener { uri ->
//                        if (uri.isSuccessful) {
//                            tshirtsFirebase.add(Image(uri.result, "tshirt$index"))
//                            Log.d("urichecking", "$tshirtsFirebase")
//                        }
//                    }
//                }
//            }
//            .addOnFailureListener {
//                Log.e("oh noo error", tshirtsFirebase.toString())
//            }
        // ***************** *****************  ***************** *****************
//        imageRecycler=findViewById(R.id.image_recycler)
//
//        imageRecycler?.layoutManager= GridLayoutManager(this, 3)
//        imageRecycler?.setHasFixedSize(true)

        // Storage permissions
//        if (ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                101
//            )
//        }
        // ***************** *****************  ***************** *****************
//        allPictures = ArrayList()
//
//        // Load images to all pictures
//        if(allPictures!!.isEmpty())
//        {
//            progressBar?.visibility = View.VISIBLE
//
//            allPictures = getAllImages()
//            // Set adapter to recycler
//            imageRecycler?.adapter = ImageAdapter(this, allPictures!!)
//            progressBar?.visibility = View.GONE
//        }
    }

//    private fun getAllImages() : ArrayList<Image> {
//        val images = ArrayList<Image>()
//
//        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//
//        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)
//
//        var cursor = this@MainActivity.contentResolver.query(allImageUri, projection,null, null, null)
//
//        try {
//            cursor!!.moveToFirst()
//            do {
//                val image=Image()
//                image.imagePath=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
//                image.imageName=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
//                images.add(image)
//            } while (cursor.moveToNext())
//            cursor.close()
//        } catch (e: Exception)
//        {
//            e.printStackTrace()
//        }
//        return images
//    }

//    private fun getImagesFromFirebase()
//    {
//        val images = ArrayList<Image>()
//
//    }