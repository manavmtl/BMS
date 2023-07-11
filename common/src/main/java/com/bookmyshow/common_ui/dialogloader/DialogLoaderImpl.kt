package com.bookmyshow.common_ui.dialogloader

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.bookmyshow.common_ui.R
import com.bookmyshow.core.DialogLoader

class DialogLoaderImpl : DialogLoader {

    override fun noInternetDialog(context: Context) {
        Log.d("noInternetDialog", "noInternetDialog: sdbckj")
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.no_internet_connection))
        builder.setMessage(context.getString(R.string.please_check_your_internet_connection_and_try_again))
        builder.setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }
}