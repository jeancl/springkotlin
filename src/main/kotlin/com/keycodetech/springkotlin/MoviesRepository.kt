package springkotlin

import org.springframework.data.repository.CrudRepository

interface MoviesRepository : CrudRepository<Movie, String>{

    fun findByTitle(title: String): Movie?

}