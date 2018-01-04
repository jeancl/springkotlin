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
    @RequestMapping("/api/movies/{id}", method = arrayOf(RequestMethod.PUT))
    fun update(@RequestBody movie:Movie)
            = repository.save(movie)

    @CrossOrigin
    @GetMapping("/api/movies/search/{title}")
    fun findByTitle(@PathVariable title:String)
            = repository.findByTitle(title)

    @CrossOrigin
    @GetMapping("/api/movies/{id}")
    fun findById(@PathVariable id:String)
        = repository.findById(id)

    @CrossOrigin
    @RequestMapping("/api/movies/{id}", method = arrayOf(RequestMethod.DELETE))
    fun remove(@PathVariable id:String)
            = repository.deleteById(id)
}