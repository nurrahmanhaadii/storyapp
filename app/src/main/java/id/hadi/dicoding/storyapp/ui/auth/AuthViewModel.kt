package id.hadi.dicoding.storyapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import id.haadii.dicoding.submission.core.model.LoginEligible
import id.haadii.dicoding.submission.core.model.Resource
import id.haadii.dicoding.submission.core.network.request.LoginRequest
import id.haadii.dicoding.submission.core.network.request.RegisterRequest
import id.haadii.dicoding.submission.core.network.response.LoginResult
import id.haadii.dicoding.submission.core.repositories.MainRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
@HiltViewModel
class AuthViewModel  @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private var _loginEligibleLiveData = MutableLiveData(LoginEligible())
    val loginEligibleLiveData : LiveData<LoginEligible> get() = _loginEligibleLiveData

    fun register(request: RegisterRequest) = flow {
        emit(repository.register(request))
    }.map {
        Resource.Success(data = it) as Resource<*>
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()

    fun login(request: LoginRequest) = flow {
        emit(repository.login(request))
    }.map {
        setIsLogin(true)
        setToken(it.loginResult.token)
        setUser(it.loginResult)
        Resource.Success(data = it) as Resource<*>
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()

    fun logout() = flow {
        setIsLogin(false)
        setToken("")
        setUser(null)
        emit(Resource.Success(data = "") as Resource<*>)
    }.onStart {
        emit(Resource.Loading)
    }.catch {
        emit(Resource.Error(data = it))
    }.asLiveData()

    fun setIsLogin(isLogin: Boolean) {
        viewModelScope.launch {
            repository.setIsLogin(isLogin)
        }
    }

    fun getIsLogin(): LiveData<Boolean> = repository.getIsLogin().asLiveData()

    fun setToken(token: String) {
        viewModelScope.launch {
            repository.setToken(token)
        }
    }

    private fun setUser(user: LoginResult?) {
        val userString = if (user == null) "" else Gson().toJson(user)
        viewModelScope.launch {
            repository.setUser(userString)
        }
    }

    fun getUser() = repository.getUser()
        .map {
            val user = Gson().fromJson(it, LoginResult::class.java)
            user
        }.asLiveData()

    fun updateLoginEligible(isEmailValid: Boolean? = null, isPasswordValid: Boolean? = null) {
        val loginEligible = _loginEligibleLiveData.value?.copy()
        isEmailValid?.let { loginEligible?.isEmailValid = it }
        isPasswordValid?.let { loginEligible?.isPasswordValid = it }
        _loginEligibleLiveData.value = loginEligible
    }

}