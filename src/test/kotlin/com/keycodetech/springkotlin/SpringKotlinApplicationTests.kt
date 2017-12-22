package springkotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringKotlinApplicationTest(@Autowired private var restTemplate: TestRestTemplate){

    @Test
    fun shouldFindAll() {
        val content = """[{"title":"Avengers: Infinity War","releaseDate":"2018-05-03T14:00:00.000+0000","watched":false},{"title":"Deadpool 2","releaseDate":"2018-05-31T14:00:00.000+0000","watched":false},{"title":"Solo: A Star Wars Story","releaseDate":"2018-05-24T14:00:00.000+0000","watched":false}]"""
        assertEquals(content, restTemplate.getForObject<String>("/api/movies"))
    }

    @Test
    fun shouldFindMovieByName() {
        val content = """[{"title":"Deadpool 2","releaseDate":"2018-05-31T14:00:00.000+0000","watched":false}]"""
        assertEquals(content, restTemplate.getForObject<String>("/api/movies/Deadpool 2"))
    }
}
