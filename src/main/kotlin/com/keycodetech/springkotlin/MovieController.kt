package springkotlin

import org.springframework.web.bind.annotation.*

@RestController
class MoviesController(private val repository: MoviesRepository) {

    @CrossOrigin
    @GetMapping("/api/movies")
    fun findAll()
            = repository.findAll()

    @CrossOrigin
    @RequestMapping("/api/movies", method = arrayOf(RequestMethod.POST))
    fun create(@RequestBody movie:Movie)
            = repository.save(movie)

    @CrossOrigin
    @GetMapping("/api/movies/{title}")
    fun findByTitle(@PathVariable title:String)
            = repository.findByTitle(title)

}