package com.keycodetech.springkotlin

import com.keycodetech.springkotlin.exceptions.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.Optional
import javax.validation.Valid

@RestController
class MoviesController(private val repository: MoviesRepository) {

	@CrossOrigin
	@GetMapping("/api/movies")
	fun findAll()
			= repository.findAll()

	@CrossOrigin
	@PostMapping("/api/movies")
	fun create(@Valid @RequestBody movie: Movie): ResponseEntity<Any> {
		val savedMovie = repository.save(movie)

		val location: URI = ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedMovie._id)
							.toUri()
		
		return ResponseEntity.created(location).build()
	}

	@CrossOrigin
	@PutMapping("/api/movies/{id}")
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
	@DeleteMapping("/api/movies/{id}")
	fun remove(@PathVariable id: String) {
		
		if(!repository.existsById(id))
			throw NotFoundException(String.format("id-%s",id))
		
		repository.deleteById(id)
	}

}