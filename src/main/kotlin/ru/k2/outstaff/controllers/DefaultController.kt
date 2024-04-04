package ru.k2.outstaff.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.k2.outstaff.service.WorkerService

@RestController
@RequestMapping("/")
class DefaultController(private val workerService: WorkerService) {

    @GetMapping("/auth")
    fun loginForm(): String {
        return "index"
    }

}