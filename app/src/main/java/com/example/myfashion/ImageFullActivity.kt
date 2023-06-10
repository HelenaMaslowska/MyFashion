package com.example.myfashion

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

class ImageFullActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full)
        val imagePath = intent.getStringExtra("imagePath")
        if (imagePath != null) {
            val bitmap1 = BitmapFactory.decodeFile(imagePath)
            //supportActionBar?.setTitle("Image") //set your own title here
            Glide.with(this)
                .asBitmap()
                .load(bitmap1)
                .into(findViewById(R.id.imageView))
        } else {
            Log.d("error load big img", imagePath.toString())
        }
    }
}