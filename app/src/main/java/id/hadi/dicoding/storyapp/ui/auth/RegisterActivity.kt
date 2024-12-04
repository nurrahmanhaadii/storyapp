package id.hadi.dicoding.storyapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import id.haadii.dicoding.submission.domain.model.BaseResponse
import id.haadii.dicoding.submission.domain.model.Resource
import id.hadi.dicoding.storyapp.databinding.ActivityRegisterBinding
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.ui.auth.LoginActivity.Companion.KEY_EMAIL
import id.hadi.dicoding.storyapp.ui.auth.LoginActivity.Companion.RESULT_REGISTER_SUCCESS
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
import retrofit2.HttpException

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


        viewModel.register(name, email, password).observe(this) {
            when(it) {
                Resource.Loading -> loading.show()
                is Resource.Error -> {
                    loading.dismiss()
                    val gson = Gson()
                    val type = object : TypeToken<BaseResponse>() {}.type
                    val response = (it.data as HttpException).response()
                    val errorResponse: BaseResponse? = gson.fromJson(response?.errorBody()?.charStream(), type)
                    Utils.showSnackBar(binding.root, errorResponse?.message.toString())
                }
                is Resource.Success -> {
                    loading.dismiss()
                    val data = it.data as BaseResponse
                    Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
                    backToLogin()
                }
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