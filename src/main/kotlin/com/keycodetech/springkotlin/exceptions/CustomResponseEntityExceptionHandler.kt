package com.keycodetech.springkotlin.exceptions

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date

@ControllerAdvice
@RestController
class CustomResponseEntityExceptionHandler : ResponseEntityExceptionHandler {
	
	constructor() : super ()
	
	@ExceptionHandler(Exception::class)
	fun handleAllException(ex: Exception, request: WebRequest) : ResponseEntity<Any> {
		
		val exceptionDetail : ExceptionDetail = ExceptionDetail(Date(),  ex.message, request.getDescription(false))
	
		return ResponseEntity(exceptionDetail, HttpStatus.INTERNAL_SERVER_ERROR)
	}
	
	@ExceptionHandler(NotFoundException::class)
	fun handleNotFoundException(ex: Exception, request: WebRequest) : ResponseEntity<Any> {
		
		val exceptionDetail : ExceptionDetail = ExceptionDetail(Date(),  ex.message, request.getDescription(false))
	
		return ResponseEntity(exceptionDetail, HttpStatus.NOT_FOUND)
	}
	
	
	override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
			headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

		val exceptionDetail : ExceptionDetail = ExceptionDetail(Date(),  "Validation Failed", ex.bindingResult.toString())
		
		return ResponseEntity(exceptionDetail, HttpStatus.BAD_REQUEST)
	}
}