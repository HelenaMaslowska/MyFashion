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

        //val imagePath = intent.getParcelableExtra("BitmapImage")
        val imagePath = intent.getStringExtra("imagePath")
        val bitmap = BitmapFactory.decodeFile(imagePath)
        if (imagePath != null) {
            Log.d("ezz", imagePath.toString())
            val bitmap1 = BitmapFactory.decodeFile(imagePath)
            //supportActionBar?.setTitle("Image")
            Glide.with(this)
                .asBitmap()
                .load(bitmap1)  // ładuj obraz z pliku
                .into(findViewById(R.id.imageView)) //tu nie bedzie działać bo on ma loadować z Bitmapy a nie ze ścieżki string
        } else {
            Log.d("ez", imagePath.toString())
        }




    }
}