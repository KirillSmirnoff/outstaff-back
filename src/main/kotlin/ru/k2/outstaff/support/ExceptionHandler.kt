package ru.k2.outstaff.support

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ru.k2.outstaff.exceptions.RoleNotFoundException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(RoleNotFoundException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(){}
}