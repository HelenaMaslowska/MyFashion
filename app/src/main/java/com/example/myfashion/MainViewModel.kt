package com.example.myfashion

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var currentTshirt = MutableLiveData<String>()       // image0
    private var currentTrousers = MutableLiveData<String>()     // image0

    private var image1 = MutableLiveData<Bitmap>()
    private var image2 = MutableLiveData<Bitmap>()
    private var image3 = MutableLiveData<Bitmap>()
    private var image4 = MutableLiveData<Bitmap>()
    private var image5 = MutableLiveData<Bitmap>()
    private var image6 = MutableLiveData<Bitmap>()

//    val imagesTshirts: ArrayList<Bitmap> = getAllTshirts()
//    val imagesTrousers: ArrayList<Bitmap> = getAllTrousers()

    val outfits: MutableLiveData<ArrayList<Pair<Char, Char>>> = MutableLiveData<ArrayList<Pair<Char, Char>>>(ArrayList())
    val outfitsTitles: MutableLiveData<ArrayList<String>> = MutableLiveData<ArrayList<String>>(ArrayList())
//Images
    fun getAllTshirts(): ArrayList<Bitmap> {
        var imgs: ArrayList<Bitmap> = ArrayList()
        imgs.add(image1.value!!)
        imgs.add(image2.value!!)
        imgs.add(image3.value!!)
        return imgs
    }
    fun getAllTrousers(): ArrayList<Bitmap> {
        var imgs: ArrayList<Bitmap> = ArrayList()
        imgs.add(image4.value!!)
        imgs.add(image5.value!!)
        imgs.add(image6.value!!)
        return imgs
    }
    fun setTshirts(text: String, image: Bitmap) {
        if (text == "image0") image1.value = image
        if (text == "image1") image2.value = image
        if (text == "image2") image3.value = image
    }
    fun setTrousers(text: String, image: Bitmap) {
        if (text == "image0") image4.value = image
        if (text == "image1") image5.value = image
        if (text == "image2") image6.value = image
    }

    fun getTshirts(text: String): LiveData<Bitmap> {
        if (text == "image0") return image1
        if (text == "image1") return image2
        if (text == "image2") return image3
        return image3
    }
    fun getTrousers(text: String): LiveData<Bitmap> {
        if (text == "image0") return image4
        if (text == "image1") return image5
        if (text == "image2") return image6
        return image6
    }

//Current tshirt
    fun setCurrentTshirt (text: String) {
        currentTshirt.value = text
    }

    fun getCurrentTshirt(): Char {
        return currentTshirt.value.toString()[5]
    }

// Current trousers
    fun setCurrentTrousers (text: String) {
        currentTrousers.value = text
    }

    fun getCurrentTrousers(): Char {
        return currentTrousers.value.toString()[5]
    }
// Outfits
    fun setOutfitsList(outfitsList: ArrayList<Pair<Char,Char>>) {
        outfits.value = outfitsList
    }

    fun getOutfitsList(): LiveData<ArrayList<Pair<Char,Char>>> {
        return outfits
    }
// Title outfits
    fun setOutfitsTitles(outfitsTitle: ArrayList<String>) {
        outfitsTitles.value = outfitsTitle
    }

    fun getOutfitsTitles(): LiveData<ArrayList<String>> {
        return outfitsTitles
    }

}