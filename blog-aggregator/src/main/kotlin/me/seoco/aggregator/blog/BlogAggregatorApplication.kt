package me.seoco.aggregator.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogAggregatorApplication

fun main(args: Array<String>) {
    runApplication<BlogAggregatorApplication>(*args)
}
