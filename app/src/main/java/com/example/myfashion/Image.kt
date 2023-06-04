package com.example.myfashion

import android.net.Uri
import com.google.android.gms.tasks.Task

class Image {
    var imagePath:Uri?=null

    var imageName:String?=null

    constructor(imagePath: Uri?, imageName: String?) {
        this.imagePath = imagePath
        this.imageName = imageName
    }
    constructor() {}
}