package me.seoco.aggregator.blog.datafetchers

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import me.seoco.aggregator.blog.generated.types.Greet
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@DgsComponent
class GreetDataFetcher {
    private final val services = listOf("post", "user")

    @DgsQuery
    suspend fun greet(@InputArgument input: String?): List<Greet> {
        return coroutineScope {
            services.map {
                async {
                    WebClient.create("http://$it-service").get().uri("/greet?input=$input").retrieve()
                }
            }
        }.awaitAll().map { it.awaitBody() as String }.map { Greet(message = it) }
    }
}
