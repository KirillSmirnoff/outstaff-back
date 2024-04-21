package ru.k2.outstaff.exceptions.handler

import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import ru.k2.outstaff.dto.ErrorResponse
import ru.k2.outstaff.exceptions.*

@ControllerAdvice
class ExceptionAdvice {


    @ExceptionHandler(value = [BusinessException::class])
    fun handleException(ex: BusinessException): ResponseEntity<ErrorResponse>{
        return ResponseEntity(ErrorResponse(ex.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [DataAccessException::class])
    fun handleException(ex: DataAccessException): ResponseEntity<ErrorResponse>{
        return ResponseEntity(ErrorResponse(ex.message!!), HttpStatus.NOT_FOUND)
    }

}