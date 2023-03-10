package me.seoco.aggregator.blog.datafetchers

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import io.netty.resolver.DefaultAddressResolverGroup
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import me.seoco.aggregator.blog.generated.types.Greet
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.netty.http.client.HttpClient

@DgsComponent
class GreetDataFetcher {
    private final val services = listOf("post", "user")
    private final val webClient: WebClient = WebClient.builder().clientConnector(
        ReactorClientHttpConnector(
            HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE)
        )
    ).build()

    @DgsQuery
    suspend fun greet(@InputArgument input: String?): List<Greet> {
        return coroutineScope {
            services.map {
                async {
                    webClient.get().uri("http://$it-service/greet?input=$input").retrieve()
                }
            }
        }.awaitAll().map { it.awaitBody() as String }.map { Greet(message = it) }
    }
}
