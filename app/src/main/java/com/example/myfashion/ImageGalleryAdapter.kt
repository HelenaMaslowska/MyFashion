package com.example.myfashion

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Gallery
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageGalleryAdapter(private val context: Context, private val imagesList: ArrayList<GalleryImage>) : RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder>() {
    class ImageViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var image: ImageView?=null
        init {
            image = itemView.findViewById(R.id.row_image)
        }
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_custom_recycler_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currImage = imagesList[position]

        Glide.with(context)
            .load(currImage.img)
            .apply(RequestOptions().centerCrop())
            .into(holder.image!!)

        holder.image?.setOnClickListener {
            val imagePath = saveBitmapToFile(context, currImage.img!!)
            val intent = Intent(context, ImageFullActivity::class.java)
            intent.putExtra("imagePath", imagePath)
            context.startActivity(intent)
        }
    }
    private fun saveBitmapToFile(context: Context, bitmap: Bitmap): String {
        val fileName = "tempImg" // nazwa pliku
        val file = File(context.cacheDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        return file.absolutePath
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}