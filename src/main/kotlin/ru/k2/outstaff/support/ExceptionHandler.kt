package ru.k2.outstaff.support

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.k2.outstaff.exceptions.RoleNotFoundException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(RoleNotFoundException::class)
    fun handleException(ex: RoleNotFoundException): ResponseEntity<String>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }
}