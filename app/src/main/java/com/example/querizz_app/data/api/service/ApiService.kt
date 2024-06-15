package com.example.querizz_app.data.api.service

import com.example.querizz_app.data.response.SumResponse
import com.example.querizz_app.data.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("prediction")
    suspend fun uploadFile(
//        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("subtitle") subtitle: RequestBody
    ): UploadResponse

    @GET("getHistories")
    suspend fun getSummaries(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): SumResponse
}