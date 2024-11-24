package id.haadii.dicoding.submission.core.helpers

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object Utils {
    fun getMultipartBodyFile(nameParameter: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            nameParameter, file.name,
            file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        )
    }
}