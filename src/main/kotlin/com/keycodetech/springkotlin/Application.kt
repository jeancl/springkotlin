package springkotlin

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.sql.Date
import java.time.LocalDate

@SpringBootApplication
class Application {

    //Initializer
    @Bean
    fun init(repository: MoviesRepository) = CommandLineRunner {

        //Database init - Remove to use in production
        repository.save(Movie(title = "Avengers: Infinity War", releaseDate = Date.valueOf(LocalDate.of(2018, 5, 4))))
        repository.save(Movie(title = "Deadpool 2", releaseDate = Date.valueOf(LocalDate.of(2018, 6, 1))))
        repository.save(Movie(title = "Solo: A Star Wars Story", releaseDate = Date.valueOf(LocalDate.of(2018, 5, 25))))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}