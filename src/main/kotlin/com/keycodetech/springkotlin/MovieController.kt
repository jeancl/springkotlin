package com.keycodetech.springkotlin

import com.keycodetech.springkotlin.exceptions.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.Optional

@RestController
class MoviesController(private val repository: MoviesRepository) {

	@CrossOrigin
	@GetMapping("/api/movies")
	fun findAll()
			= repository.findAll()

	@CrossOrigin
	@RequestMapping("/api/movies", method = arrayOf(RequestMethod.POST))
	fun create(@RequestBody movie: Movie): ResponseEntity<Any> {
		val savedMovie = repository.save(movie)

		val location: URI = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedMovie._id)
							.toUri()
		
		return ResponseEntity.created(location).build()
	}

	@CrossOrigin
	@RequestMapping("/api/movies/{id}", method = arrayOf(RequestMethod.PUT))
	fun update(@RequestBody movie: Movie)
			= repository.save(movie)

	@CrossOrigin
	@GetMapping("/api/movies/search/{title}")
	fun findByTitle(@PathVariable title: String)
			= repository.findByTitle(title)

	@CrossOrigin
	@GetMapping("/api/movies/{id}")
	fun findById(@PathVariable id: String): Optional<Movie> {
		val movie = repository.findById(id)
		
		if(!movie.isPresent())
			throw NotFoundException(String.format("id-%s",id))
		
		return movie
	}

	@CrossOrigin
	@RequestMapping("/api/movies/{id}", method = arrayOf(RequestMethod.DELETE))
	fun remove(@PathVariable id: String)
			= repository.deleteById(id)
}