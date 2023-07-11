package com.bookmyshow.feature_one.utils

import android.view.View

fun String?.nul(): String {
    return this ?: ""
}

fun Int?.nul(): Int {
    return this ?: 0
}

fun <T> ArrayList<T>?.nul(): ArrayList<T> {
    return this ?: arrayListOf()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}