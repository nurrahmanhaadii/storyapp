package id.hadi.dicoding.storyapp.data.network.api

import id.hadi.dicoding.storyapp.data.network.request.LoginRequest
import id.hadi.dicoding.storyapp.data.network.request.RegisterRequest
import id.hadi.dicoding.storyapp.data.network.response.BaseResponse
import id.hadi.dicoding.storyapp.data.network.response.LoginResponse
import id.hadi.dicoding.storyapp.data.network.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by nurrahmanhaadii on 12,March,2024
 */
interface ApiService {

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): BaseResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @GET("stories")
    suspend fun getAllStoriesWithLocation(
        @Query("location") location : Int = 1
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStories(
        @Path("id") id: String
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("lat") lat: RequestBody?,
        @Part("lon") long: RequestBody?
    ): BaseResponse
}