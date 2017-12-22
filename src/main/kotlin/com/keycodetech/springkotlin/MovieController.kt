package springkotlin

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MoviesController(private val repository: MoviesRepository) {

    @CrossOrigin
    @GetMapping("/api/movies")
    fun findAll()
            = repository.findAll()

    @CrossOrigin
    @GetMapping("/api/movies/{title}")
    fun findByTitle(@PathVariable title:String)
            = repository.findByTitle(title)

}