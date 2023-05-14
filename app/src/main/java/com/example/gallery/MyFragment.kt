package com.example.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream

class MyFragment : Fragment(R.layout.fragment_blank), GaleryAdapter.OnImageClickListeren  {
    private var images: ArrayList<Images> = ArrayList<Images>()
    private lateinit var rv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addImages()
        initRecyclerView(view)
        sortElements()

    }


    private fun initRecyclerView(view: View) {
        rv = view.findViewById(R.id.rv1)
        rv.layoutManager = GridLayoutManager(activity,2)
        rv.adapter = GaleryAdapter(images,this)
    }

    private fun addImages(){
        addToImages(R.drawable.img)
        addToImages(R.drawable.img_5)
        addToImages(R.drawable.img_2)
        addToImages(R.drawable.img_3)
        addToImages(R.drawable.img_4)
        addToImages(R.drawable.img_5)
    }

    @SuppressLint("SdCardPath")
    private fun addToImages(drawable : Int){

        val image = Images("kot1$drawable","Kot")
        images.add(image)
        addToCatalog("kot1$drawable",drawable)

    }

    private fun addToCatalog(name : String, drawable : Int){
        val myBitmap = BitmapFactory.decodeResource(resources, drawable)
        val cw = ContextWrapper(context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val myImageFile = File(directory, name)


        val outputStream = FileOutputStream(myImageFile)
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortElements(){
        val list = images.sortedByDescending { it.rate }
        images.clear()
        images.addAll(list)
        rv.adapter?.notifyDataSetChanged()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("images", images)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            images = savedInstanceState.getSerializable("images") as ArrayList<Images>
            sortElements()
        }

    }

    override fun onImgClick(position: Int) {
        val intent = Intent(activity,ImageDetails::class.java)
        if(images.isNotEmpty()) {
            intent.putExtra("path", images[position].path)
            intent.putExtra("data", images[position].data)
        }
        resultLauncher.launch(intent)
    }

    override fun onResume() {
        super.onResume()
        sortElements()
    }

    @SuppressLint("NotifyDataSetChanged")
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result-> val data = result.data
        if (data != null) {
            val name = data.getStringExtra("path").toString()
            data.getSerializableExtra("rate")?.let {
                for( i in images){
                    if (i.path == name){
                        i.rate = it as Float
                    }

                }
            }
        }
    }

}