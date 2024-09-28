package com.mohamed.utils.ext

import android.widget.ImageView
import com.bumptech.glide.Glide

private const val TAG = "ImgExt"

fun ImageView.load(url: String) {
    Glide.with(this).load(url)
        .into(this)
}