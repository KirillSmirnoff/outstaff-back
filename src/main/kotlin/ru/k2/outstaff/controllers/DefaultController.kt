package ru.k2.outstaff.controllers

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.k2.outstaff.service.WorkerService
import java.security.Principal

@RestController
@RequestMapping("/")
class DefaultController(private val workerService: WorkerService) {

    @GetMapping("/home")
    fun home(model: Model, principal: Principal): String {
        val workers = workerService.getAllWorkers()

        model.addAttribute("username", principal.name)
        model.addAttribute("person", workers)
        return "work-sheet"
    }

    @PostMapping("/home")
    fun homeAfterLoging(model: Model): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as UserDetails

        val workers = workerService.getAllWorkers()

        model.addAttribute("person", workers)
        model.addAttribute("username", user.username)
        return "work-sheet"
    }

    @GetMapping("/auth")
    fun loginForm(): String {
        return "index"
    }

}