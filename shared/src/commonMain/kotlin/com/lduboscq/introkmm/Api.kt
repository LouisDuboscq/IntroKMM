package com.lduboscq.introkmm

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Api {

    companion object {
        private const val URL = "https://api.spacexdata.com/v4/launches"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun fetch(): List<RocketLaunch> =
        httpClient.get("https://api.spacexdata.com/v4/launches").body()
}
