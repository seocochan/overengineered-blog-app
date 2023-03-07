package me.seoco.aggregator.blog.datafetchers

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import me.seoco.aggregator.blog.generated.types.Greet

@DgsComponent
class GreetDataFetcher {

    @DgsQuery
    suspend fun greet(@InputArgument input: String?): List<Greet> {
        return listOf(Greet(message = input ?: "hi"), Greet(message = "uwu"))
    }
}