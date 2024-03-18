package id.hadi.dicoding.storyapp.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.net.Uri
import android.util.TypedValue
import android.view.View
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by nurrahmanhaadii on 14,March,2024
 */
object Utils {
    private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

    fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile(timeStamp, ".jpg", filesDir)
    }

    fun getMultipartBodyFile(nameParameter: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            nameParameter, file.name,
            file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        )
    }

    @SuppressLint("Range")
    fun uriToFile(context: Context, uri: Uri?): File? {
        if (uri == null) return null
        if ("file" == uri.scheme) {
            val path = uri.path ?: return null
            return File(path)
        }
        // this for content uri
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        var file: File? = null
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getColumnIndex("_data") < 0) return null
            val filePath = cursor.getString(cursor.getColumnIndex("_data"))
            if (filePath != null) {
                file = File(filePath)
            }
            cursor.close()
        }
        return file
    }

    fun getPrimaryColor(context: Context): Int {
        val typedValue = TypedValue()
        val a: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(androidx.appcompat.R.attr.colorPrimary))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}