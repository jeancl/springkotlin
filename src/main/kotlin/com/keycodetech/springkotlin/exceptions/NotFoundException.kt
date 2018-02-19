package com.keycodetech.springkotlin.exceptions

import java.lang.RuntimeException
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException : RuntimeException {
	constructor(message: String) : super(message)
}