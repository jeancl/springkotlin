package springkotlin

import org.springframework.data.repository.CrudRepository

interface MoviesRepository : CrudRepository<Movie, Long>{

    fun findByTitle(title: String): List<Movie>

}