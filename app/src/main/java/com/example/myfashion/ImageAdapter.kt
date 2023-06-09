package com.example.myfashion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myfashion.ui.outfits.OutfitsFragment

class ImageAdapter(private val context: Context, private val images: ArrayList<Image>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private lateinit var viewModel: MainViewModel

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgTitle = itemView.findViewById<TextView>(R.id.outfit_title)
        val img1 = itemView.findViewById<ImageView>(R.id.tshirtImg)
        val img2 = itemView.findViewById<ImageView>(R.id.trousersImg)
        fun bindView(image: Image) {
            img1.setImageBitmap(image.src1)
            img2.setImageBitmap(image.src2)
            imgTitle.text = image.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_images, parent, false))

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position])
    }
}


/*

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image1: ImageView?=null
        init {
            image1 = itemView?.findViewById(R.id.tshirtImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentImage = imagesList[position]
        Glide.with(context)
            .load(currentImage)
            .apply(RequestOptions().centerCrop())
            .into(holder.image1!!)

//        holder.image?.setOnClickListener {
//            val intent = Intent(context, ImageFullActivity::class.java)
//            intent.putExtra("path", currentImage.imagePath)
//            intent.putExtra("name", currentImage.imageName)
//            context.startActivity(intent)
//        }                                                                              odkomentować jak już zrobię tę galerię, to wyświetla obrazek po kliknięciu w niego


    }

 */