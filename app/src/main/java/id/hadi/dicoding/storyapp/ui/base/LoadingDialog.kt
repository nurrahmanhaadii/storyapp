package id.hadi.dicoding.storyapp.ui.base

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import id.hadi.dicoding.storyapp.R
import id.hadi.dicoding.storyapp.databinding.DialogLoadingBinding

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
class LoadingDialog(context: Context, message: String? = null) {

    private val dialog: AlertDialog

    init {
        Log.d("LoadingDialog", message ?: "null")
        val inflater = LayoutInflater.from(context)

        val binding = DialogLoadingBinding.inflate(inflater, null, false) // Inflate your layout

        if (message != null) {
            binding.loadingText.visibility = View.VISIBLE
            binding.loadingText.text = message
        }

        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        builder.setCancelable(false) // Optional: prevent user from dismissing by tapping outside

        dialog = builder.create()
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}