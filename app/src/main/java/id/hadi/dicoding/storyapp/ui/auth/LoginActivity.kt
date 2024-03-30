package id.hadi.dicoding.storyapp.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import id.hadi.dicoding.storyapp.data.model.Resource
import id.hadi.dicoding.storyapp.data.network.request.LoginRequest
import id.hadi.dicoding.storyapp.data.network.response.ErrorResponse
import id.hadi.dicoding.storyapp.databinding.ActivityLoginBinding
import id.hadi.dicoding.storyapp.helper.Utils
import id.hadi.dicoding.storyapp.ui.base.LoadingDialog
import id.hadi.dicoding.storyapp.ui.home.MainActivity
import java.lang.Error


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    private lateinit var loading: LoadingDialog

    private val registerLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_REGISTER_SUCCESS) {
            val data: Intent? = result.data
            val email = data?.getStringExtra(KEY_EMAIL)
            binding.edLoginEmail.setText(email)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoginStatus()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()

        loading = LoadingDialog(this, "Logging In...")

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            registerLauncher.launch(intent)
        }
        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.edLoginPassword.doAfterTextChanged {
            if (binding.edLoginPassword.isValid()) {
                viewModel.updateLoginEligible(isPasswordValid = true)
            } else {
                viewModel.updateLoginEligible(isPasswordValid = false)
            }
        }

        binding.edLoginEmail.doAfterTextChanged {
            if (binding.edLoginEmail.isEmailValid(it.toString())) {
                viewModel.updateLoginEligible(isEmailValid = true)
            } else {
                viewModel.updateLoginEligible(isEmailValid = false)
            }
        }

        observeLoginEligible()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.tvSignUp, View.ALPHA, 1f).setDuration(100)
        val email = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(300)
        val password = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(300)
        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }
        AnimatorSet().apply {
            playSequentially(email, password, together)
            start()
        }
    }

    private fun login() {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()
        val request = LoginRequest(email, password)

        viewModel.login(request).observe(this) {
            when (it) {
                Resource.Loading -> loading.show()
                is Resource.Success -> {
                    loading.dismiss()
                    goToMainActivity()
                }
                is Resource.Error -> {
                    loading.dismiss()
                    Utils.showSnackBar(binding.root, it.data.toString())
                }
            }
        }
    }

    private fun observeLoginStatus() {
        viewModel.getIsLogin().observe(this) {
            if (it) {
                goToMainActivity()
            }
        }
    }

    private fun observeLoginEligible() {
        viewModel.loginEligibleLiveData.observe(this) {
            if (it.isEmailValid && it.isPasswordValid) {
                binding.btnLogin.apply {
                    isEnabled = true
                    backgroundTintList = ColorStateList.valueOf(Utils.getPrimaryColor(this@LoginActivity))
                }
            } else {
                binding.btnLogin.apply {

                    isEnabled = false
                    backgroundTintList = ColorStateList.valueOf(Color.LTGRAY)
                }
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val RESULT_REGISTER_SUCCESS = 200
        const val KEY_EMAIL = "key_email"
    }
}