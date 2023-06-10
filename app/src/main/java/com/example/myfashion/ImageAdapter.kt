package com.example.myfashion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(private val context: Context, private val images: ArrayList<Image>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_images, parent, false))
    }
    private fun deleteItem(position: Int) {
        images.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, images.size)
    }
    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position])
    }
}