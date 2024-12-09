package id.hadi.dicoding.storyapp.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
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
            if (cursor.getColumnIndex("_data") < 0) {
                return getFileFromContentUriExisting(context, uri)
            }
            val filePath = cursor.getString(cursor.getColumnIndex("_data"))
            if (filePath != null) {
                file = File(filePath)
            }
            cursor.close()
        }
        return file
    }

    private fun getFileFromContentUriExisting(context: Context, contentUri: Uri): File? {
        val fileDescriptor = context.contentResolver.openFileDescriptor(contentUri, "r") ?: return null
        val fileName = getFileName(contentUri)
        val newFile = File(context.cacheDir, fileName)

        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        fileDescriptor.close()
        val outputStream = newFile.outputStream()

        val buffer = ByteArray(BUFFER_SIZE)
        var bytesRead: Int

        try {
            while (inputStream.read(buffer).also { bytesRead = it } > 0) {
                outputStream.write(buffer, 0, bytesRead)
            }
            return newFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            inputStream.close()
            outputStream.close()
        }
    }

    private fun getFileName(contentUri: Uri): String {
        val fileName = contentUri.lastPathSegment
        return if (fileName.isNullOrEmpty()) {
            "temp_file" // Default filename if not provided by URI
        } else {
            fileName
        }
    }

    private const val BUFFER_SIZE = 4096

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

    fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun compressAndWriteImage(context: Context, imagePath: String?, targetSize: Long): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "IMG_$timeStamp.jpg"

        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val targetFile = File(storageDir, fileName)

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true // Only decode image size initially
        BitmapFactory.decodeFile(imagePath, options)

        val originalWidth = options.outWidth
        val originalHeight = options.outHeight
        var inSampleSize = 1

        // Calculate inSampleSize based on target size and original image dimensions
        while (originalWidth / inSampleSize > MAX_IMAGE_DIMENSION || originalHeight / inSampleSize > MAX_IMAGE_DIMENSION) {
            inSampleSize *= 2
        }

        options.inJustDecodeBounds = false // Decode image data now with calculated inSampleSize
        options.inSampleSize = inSampleSize

        val bitmap = BitmapFactory.decodeFile(imagePath, options) ?: return null

        val bos = ByteArrayOutputStream()
        var currentQuality = QUALITY
        var compressed: Boolean

        do {
            bos.reset() // Clear byte stream for each compression attempt
            bitmap.compress(Bitmap.CompressFormat.JPEG, currentQuality, bos)
            compressed = bos.toByteArray().size.toLong() <= targetSize
            if (!compressed) {
                currentQuality -= COMPRESSION_DECREMENT
            }
        } while (!compressed && currentQuality >= MIN_QUALITY)

        val byteArray = bos.toByteArray()
        targetFile.outputStream().use { outputStream ->
            outputStream.write(byteArray)
            outputStream.flush()
        }
        return targetFile

    }

    private const val MAX_IMAGE_DIMENSION = 1024 // Adjust based on your needs (in pixels)
    private const val MIN_QUALITY = 60
    private const val COMPRESSION_DECREMENT = 10 // Adjust minimum quality (0-100)
    private var QUALITY = 80 // Initial quality (0-100)
}