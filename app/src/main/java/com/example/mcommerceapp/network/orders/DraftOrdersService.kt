package com.example.mcommerceapp.network.orders

import com.example.mcommerceapp.model.Keys
import com.google.gson.JsonObject
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface DraftOrdersService  {

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("draft_orders/{resource}.json")
    suspend fun getDraftOrderByID(
        @Path("resource", encoded = true) orderID: String
    ): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @GET("draft_orders.json?limit=250")
    suspend fun getAllDraftOrders(): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @POST("draft_orders.json")
    suspend fun createDraftOrder(@Body requestBody: RequestBody): Response<JsonObject>


    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @PUT("draft_orders/{resource}.json")
    suspend fun updateDraftOrder( @Path("resource", encoded = true) orderID: String,@Body requestBody: RequestBody): Response<JsonObject>

    @Headers(
        "X-Shopify-Access-Token:  ${Keys.ACCESS_TOKEN}",
        "Content-Type: application/json"
    )
    @DELETE("draft_orders/{resource}.json")
    suspend fun deleteDraftOrderByID(
        @Path("resource", encoded = true) orderID: Long
    ): Response<JsonObject>


}