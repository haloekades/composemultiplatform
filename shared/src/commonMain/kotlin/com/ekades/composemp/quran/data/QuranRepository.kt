package com.ekades.composemp.quran.data

import com.ekades.composemp.quran.domain.model.QuranSurah
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class QuranRepository {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            logger = object: Logger {
                override fun log(message: String) {
                    println("Ktor-Client Log <------> $message")
                }
            }
        }
        defaultRequest {
            header("Content-Type", "application/json")
            url("https://equran.id")
        }

        install(HttpTimeout){
            connectTimeoutMillis = 15000
            requestTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }
    }

    suspend fun fetchSurahList(): List<QuranSurah> {
        val response: HttpResponse = client.get("/api/surat")
        return response.body()
    }

    suspend fun fetchSurahDetail(number: Int): QuranSurah {
        val response: HttpResponse = client.get("/api/surat/$number")
        return response.body()
    }
}