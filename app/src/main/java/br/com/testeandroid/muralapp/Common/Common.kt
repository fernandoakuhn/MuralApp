package br.com.testeandroid.muralapp.Common

import br.com.testeandroid.muralapp.Interface.RetrofitService
import br.com.testeandroid.muralapp.Retrofit.RetrofitClient

object Common {
    private val BASE_URL = "http://jsonplaceholder.typicode.com/posts"

    val retrofitService: RetrofitService
    get() = RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}