package id.hadi.dicoding.storyapp.helper

import android.content.Intent
import android.os.Build
import android.os.Parcelable

/**
 * Created by nurrahmanhaadii on 13,March,2024
 */

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}