package br.com.testeandroid.muralapp

import com.google.gson.annotations.SerializedName

data class Posts(
    @SerializedName("userID")
    val userID: Integer,
    @SerializedName("id")
    val id: Integer,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)