package me.seoco.service.post.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {
    
    @GetMapping("/greet")
    suspend fun greet(@RequestParam input: String?): String {
        return "hello ${input ?: "person"} from post service"
    }
}
