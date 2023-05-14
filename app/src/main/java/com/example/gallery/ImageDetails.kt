package com.example.gallery

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import java.io.File

class ImageDetails : AppCompatActivity() {

    lateinit var ratingBar : RatingBar
    var path: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentManager = supportFragmentManager
        val currentOrientation = resources.configuration.orientation
        setContentView(R.layout.image_details)
        path = intent.getStringExtra("path").toString()
        val data = intent.getStringExtra("data")

        // Create the fragment instance and set the data as arguments
        val fragment = if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            val portraitFragment = FragmentPortrait()
            val args = Bundle()
            args.putString("path", path)
            args.putString("data", data)
            portraitFragment.arguments = args
            portraitFragment
        } else {
            val horizontalFragment = FragmentHorizontal()
            val args = Bundle()
            args.putString("path", path)
            args.putString("data", data)
            horizontalFragment.arguments = args
            horizontalFragment
        }

        // Replace the fragment
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}

    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //    setContentView(R.layout.image_details)
    //    ratingBar = findViewById(R.id.ratingBar)
    //    val dataWidget = findViewById<TextView>(R.id.textView3)
    //    val imgWidget = findViewById<ImageView>(R.id.imageView2)
    //    val backButton = findViewById<Button>(R.id.button)
    //
    //
    //    backButton.setOnClickListener {
    //        rememberContent()
    //    }
    //    if(data!= null){
    //        dataWidget.text = data
    //    }
    //    val cw = ContextWrapper(this)
    //    val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
    //    val myImageFile = File(directory, path)
//
//
    //    Picasso.get().load(myImageFile).into(imgWidget)
    //}
//
    //private fun rememberContent() {
    //    val intent = Intent()
    //    intent.putExtra("rate",ratingBar.rating)
    //    intent.putExtra("path",path)
    //    setResult(Activity.RESULT_OK,intent)
    //    finish()
//
    //}
