package com.bookmyshow.common_ui.toastLoader

import android.content.Context
import android.widget.Toast
import com.bookmyshow.core.ToastLoader
import javax.inject.Inject

class ToastImpl  : ToastLoader {
    override fun showToastShort(context: Context,msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}