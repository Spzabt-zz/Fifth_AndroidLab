package com.example.lab_4_stoida.api

import com.example.lab_4_stoida.Users
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("d4867d8b-b5d5-4a48-a4ab-79131b5809b8")
    fun getItems() : Call<List<Users>>
}