package com.example.gallery

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class GaleryViewHolder(var itemView: View, var onImageClickListener: GaleryAdapter.OnImageClickListeren) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var imgView : ImageView
    var view = itemView

    init {
        imgView = itemView.findViewById(R.id.imageView)
        imgView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
       onImageClickListener.onImgClick(adapterPosition)
    }
}