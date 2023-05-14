package com.example.gallery

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import java.io.File

class FragmentPortrait : Fragment(R.layout.fragment_portrait){

    lateinit var ratingBar : RatingBar
    var path: String = ""
    var data : String =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_portrait, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ratingBar = view.findViewById(R.id.ratingBar)
        path = arguments?.getString("path").toString()
        data = arguments?.getString("data").toString()
        val dataWidget = view.findViewById<TextView>(R.id.textView3)
        val imgWidget = view.findViewById<ImageView>(R.id.imageView2)
        val backButton = view.findViewById<Button>(R.id.button)

        backButton.setOnClickListener {
            rememberContent()
        }
        dataWidget.text = data
        val cw = ContextWrapper(view.context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val myImageFile = File(directory, path)
        Picasso.get().load(myImageFile).into(imgWidget)
    }


    private fun rememberContent() {
        val intent = Intent()
        intent.putExtra("rate",ratingBar.rating )
        intent.putExtra("path",path)
        requireActivity().setResult(Activity.RESULT_OK,intent)
        requireActivity().finish()
    }
}