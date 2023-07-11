package com.bookmyshow.common_ui.di

import android.content.Context
import com.bookmyshow.common_ui.toastLoader.ToastImpl
import com.bookmyshow.common_ui.dialogloader.DialogLoaderImpl
import com.bookmyshow.common_ui.imageloader.ImageLoaderImpl
import com.bookmyshow.core.DialogLoader
import com.bookmyshow.core.ImageLoader
import com.bookmyshow.core.ToastLoader
import dagger.Module
import dagger.Provides

@Module
class CommonModule {

    @Provides
    fun getImageLoader(
        context: Context
    ): ImageLoader {
        return ImageLoaderImpl(
            context = context
        )
    }

    @Provides
    fun getDialogLoader():DialogLoader{
        return DialogLoaderImpl()
    }

    @Provides
    fun getToastsLoader():ToastLoader{
        return ToastImpl()
    }
}