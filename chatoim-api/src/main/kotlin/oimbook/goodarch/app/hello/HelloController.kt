package oimbook.goodarch.app.hello

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/hello")
class HelloController {

    @GetMapping
    fun hello(): Map<String, String> = mapOf("message" to "hello chatoim!")

}