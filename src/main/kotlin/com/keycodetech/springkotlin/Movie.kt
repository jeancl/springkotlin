package com.keycodetech.springkotlin

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Document
class Movie(
        @Indexed(unique = true)
        var title: String = "",
        var releaseDate: Date? = null,
        var watched: Boolean = false
) {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        lateinit var _id: String
}