package com.example.myfashion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageFullActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full)

        val imagePath = intent.getStringExtra("path")

        supportActionBar?.setTitle("Image")
        Glide.with(this)
            .load(imagePath)
            .into(findViewById(R.id.imageView)) //tu nie bedzie działać bo on ma loadować z Bitmapy a nie ze ścieżki string
    }
}