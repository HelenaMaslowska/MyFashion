package com.example.myfashion

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var imageRecycler: RecyclerView?=null
    private var progressBar: ProgressBar?=null
    private var allPictures: ArrayList<Image>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ***************** ***************** ***************** *****************
        // ***************** FIREBASE SETUP
        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        // ***************** *****************  ***************** *****************


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_outfits, R.id.navigation_gallery))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        imageRecycler=findViewById(R.id.image_recycler) image adapter, image, imagefullactivity
//        progressBar=findViewById(R.id.recycler_progress)
//
//        imageRecycler?.layoutManager=GridLayoutManager(this, 3)
//        imageRecycler?.setHasFixedSize(true)

        // Storage permissions
//        if (ContextCompat.checkSelfPermission(this@MainActivity,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                101
//            )
//        }






        allPictures = ArrayList()

        // Load images to all pictures
        if(allPictures!!.isEmpty())
        {
            progressBar?.visibility = View.VISIBLE

            allPictures = getAllImages()
            // Set adapter to recycler
            imageRecycler?.adapter = ImageAdapter(this, allPictures!!)
            progressBar?.visibility = View.GONE
        }
    }

    private fun getAllImages() : ArrayList<Image> {
        val images = ArrayList<Image>()

        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        var cursor = this@MainActivity.contentResolver.query(allImageUri, projection,null, null, null)

        try {
            cursor!!.moveToFirst()
            do {
                val image=Image()
                image.imagePath=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
            } while (cursor.moveToNext())
            cursor.close()
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
        return images
    }

    private fun getImagesFromFirebase()
    {
        val images = ArrayList<Image>()

    }
}