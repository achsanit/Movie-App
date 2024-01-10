package com.achsanit.movieapp.utils

import android.view.View
import coil.request.ImageRequest
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

fun View.makeGone() {
    this.visibility = View.GONE
}
fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun ImageRequest.Builder.setShimmerPlaceholder() {
    val shimmer =
        Shimmer.AlphaHighlightBuilder()// The attributes for a ShimmerDrawable is set by this builder
            .setFixedWidth(250)
            .setFixedHeight(250)
            .setDuration(1800) // how long the shimmering animation takes to do one full sweep
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

// This is the placeholder for the imageView
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

//    Set into placeholder
    placeholder(shimmerDrawable)
}

fun Int.toGender(): String {
    return when(this) {
        0 -> "Not specified"
        1 -> "Female"
        2 -> "Male"
        3 -> "Non-binary"
        else -> "Not set"
    }
}

fun Int.minutesToHourMinute(): String {
    val hour = this / 60
    val minute = this % 60

    return "${hour}h ${minute}m"
}