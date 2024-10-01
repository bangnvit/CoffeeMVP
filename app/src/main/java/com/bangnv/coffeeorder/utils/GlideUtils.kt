package com.bangnv.coffeeorder.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bangnv.coffeeorder.R

object GlideUtils {
    @JvmStatic
    fun loadUrlBanner(url: String?, imageView: ImageView) {
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.img_no_image)
            return
        }
        Glide.with(imageView.context)
                .load(url)
                .error(R.drawable.img_no_image)
                .dontAnimate()
                .into(imageView)
    }

    @JvmStatic
    fun loadUrl(url: String?, imageView: ImageView) {
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.image_no_available)
            return
        }
        Glide.with(imageView.context)
                .load(url)
                .error(R.drawable.image_no_available)
                .dontAnimate()
                .into(imageView)
    }

    @JvmStatic
    fun loadUrl300(url: String?, imageView: ImageView) {
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.image_no_available)
            return
        }
        Glide.with(imageView.context)
            .load(url)
            .override(300, 300)
            .error(R.drawable.image_no_available)
            .dontAnimate()
            .into(imageView)
    }
}