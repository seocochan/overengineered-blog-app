package me.seoco.aggregator.blog.datafetchers

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import io.netty.resolver.DefaultAddressResolverGroup
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import me.seoco.aggregator.blog.generated.types.Greet
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.netty.http.client.HttpClient
import java.util.*

@DgsComponent
class GreetDataFetcher {
    private final val services = listOf("post", "user", "rank")
    private final val webClient: WebClient = WebClient.builder().clientConnector(
        ReactorClientHttpConnector(
            HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE)
        )
    ).build()

    @Value("\${app.foo}")
    lateinit var foo: String

    @PreAuthorize("hasRole('ADMIN')")
    @DgsQuery
    suspend fun greet(
        @InputArgument input: String?,
    ): List<Greet> {
        println(ReactiveSecurityContextHolder.getContext()?.awaitSingle()?.authentication?.principal)
        return coroutineScope {
            services.map {
                async {
                    webClient.get().uri("http://$it-service/greet?input=${input ?: foo}").retrieve()
                }
            }
        }.awaitAll().map { it.awaitBody() as String }.map { Greet(message = it) }
    }

}
