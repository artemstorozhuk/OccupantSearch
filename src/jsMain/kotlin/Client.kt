import com.occupantsearch.occupant.Occupant
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.browser.window

class Client {
    private val endpoint = window.location.origin

    private val jsonClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun getOccupants(query: String, page: Int): List<Occupant> =
        jsonClient.get("$endpoint/occupants") {
            parameter("query", query)
            parameter("page", page)
        }
}
