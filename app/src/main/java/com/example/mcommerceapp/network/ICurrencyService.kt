package com.example.mcommerceapp.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
/// marawan key  we2oc5jxArbNIo4NCN1Q4m3rGw6yRHcM
// karam key i0sSwInnqkf7vn7j58PZ6hmFxamSHCKB
// nayra key ZckheMOjLcIIs5QIjoWrVXQL6geNwOPA
interface ICurrencyService {
    @Headers("apikey:ZckheMOjLcIIs5QIjoWrVXQL6geNwOPA")
    @GET("symbols")
    suspend fun getAllCurrencySymbols(): Response<JsonObject>


    @Headers("apikey:ZckheMOjLcIIs5QIjoWrVXQL6geNwOPA")
    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<JsonObject>
}