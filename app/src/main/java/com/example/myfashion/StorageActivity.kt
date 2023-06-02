package com.example.myfashion

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfashion.databinding.ActivityStorageBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class StorageActivity : AppCompatActivity() {

    lateinit var binding: ActivityStorageBinding
    lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_storage)

        binding.selectImageBtn.setOnClickListener {
            selectImage()
        }

        binding.uploadImageBtn.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading file...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        storageReference.putFile(ImageUri).
                addOnSuccessListener {
                    binding.firebaseImage.setImageURI(null)
                    Toast.makeText(this@StorageActivity, "Successfuly uploaded", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                }.addOnCanceledListener {
                    if (progressDialog.isShowing) progressDialog.dismiss()
                        Toast.makeText(this@StorageActivity,"Failed", Toast.LENGTH_SHORT).show()
                }
    }

    private fun selectImage () {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            binding.firebaseImage.setImageURI(ImageUri)
        }
    }
}












