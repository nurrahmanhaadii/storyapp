package id.hadi.dicoding.storyapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hadi.dicoding.storyapp.R
import id.hadi.dicoding.storyapp.data.model.Resource
import id.hadi.dicoding.storyapp.data.network.request.RegisterRequest
import id.hadi.dicoding.storyapp.data.network.response.BaseResponse
import id.hadi.dicoding.storyapp.databinding.ActivityRegisterBinding
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.ui.auth.LoginActivity.Companion.KEY_EMAIL
import id.hadi.dicoding.storyapp.ui.auth.LoginActivity.Companion.RESULT_REGISTER_SUCCESS
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    private val loading: LoadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            registerButton.setOnClickListener {
                register()
            }
            tvLogin.setOnClickListener {
                finish()
            }
        }
    }

    private fun register() {
        val name = binding.edRegisterName.text.toString()
        val email = binding.edRegisterEmail.text.toString()
        val password = binding.edRegisterPassword.text.toString()

        val request = RegisterRequest(name, email, password)

        viewModel.register(request).observe(this) {
            when(it) {
                Resource.Loading -> loading.show()
                is Resource.Error -> {
                    loading.dismiss()
                    Utils.showSnackBar(binding.root, it.data.toString())
                }
                is Resource.Success -> {
                    loading.dismiss()
                    val data = it.data as BaseResponse
                    Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
                    backToLogin()
                }

                else -> Unit
            }
        }
    }

    private fun backToLogin() {
        val intent = Intent()
        intent.putExtra(KEY_EMAIL, binding.edRegisterEmail.text.toString())
        setResult(RESULT_REGISTER_SUCCESS, intent)
        finish()
    }
}