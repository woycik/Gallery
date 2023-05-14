package com.example.gallery

import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.File

class GaleryAdapter(var img: ArrayList<Images>, var onImgClickListener: OnImageClickListeren): RecyclerView.Adapter<GaleryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell,parent,false)
        view.layoutParams.height = 1000
        return GaleryViewHolder(view,onImgClickListener)
    }

    override fun getItemCount(): Int {
        return img.size
    }

    override fun onBindViewHolder(holder: GaleryViewHolder, position: Int) {
        //if (ContextCompat.checkSelfPermission(holder.view.context, Manifest.permission.READ_EXTERNAL_STORAGE)
        //    != PackageManager.PERMISSION_GRANTED) {
        //    ActivityCompat.requestPermissions(
        //        holder.view.context as Activity,
        //        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
        //        1)
        //} else {
        //    Log.e("tag","I have permission")
        //    val file= File(img[position].path)
        //    Picasso.get().load(file).into(holder.imgView)
        //}
        val cw = ContextWrapper(holder.view.context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val myImageFile = File(directory, img[position].path)

        Picasso.get().load(myImageFile ).into(holder.imgView)


    }



    interface OnImageClickListeren{
        fun onImgClick(position : Int)
    }
}