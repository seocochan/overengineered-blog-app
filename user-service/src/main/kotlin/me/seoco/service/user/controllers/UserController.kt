package me.seoco.service.user.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/greet")
    suspend fun greet(@RequestParam input: String?): String {
        return "hello ${input ?: "person"} from user service"
    }
}
