package com.keycodetech.springkotlin

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.validation.constraints.Future
import javax.validation.constraints.Size

@Document
class Movie(
        @Indexed(unique = true)
		@get:Size(min=2, message="Name should have more than 2 characters")
        var title: String = "",
		@get:Future(message="Release Date should be in the future")
        var releaseDate: Date? = null,
        var watched: Boolean = false
) {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        lateinit var _id: String
}