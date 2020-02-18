package br.com.testeandroid.muralapp.Interface

import br.com.testeandroid.muralapp.Posts
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("posts")
    fun getPostsList(): Call<MutableList<Posts>>
}