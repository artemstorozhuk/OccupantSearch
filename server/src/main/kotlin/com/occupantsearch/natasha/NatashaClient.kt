package com.occupantsearch.natasha

import com.occupantsearch.json.parseJson
import com.occupantsearch.lang.retryUntilSuccess
import com.occupantsearch.properties.PropertiesController
import org.koin.core.annotation.Single
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Single
class NatashaClient(
    propertiesController: PropertiesController
) {
    private val url = propertiesController["natasha"]["url"]!!
    private val client = HttpClient.newBuilder().build()

    fun process(text: String) =
        HttpRequest.newBuilder()
            .uri(URI.create(url))
            .POST(HttpRequest.BodyPublishers.ofString(text))
            .build()
            .let {
                retryUntilSuccess(lambda = {
                    client.send(it, HttpResponse.BodyHandlers.ofString())
                        .body()
                        .parseJson(NatashaResponse::class.java)
                })!!
            }
}