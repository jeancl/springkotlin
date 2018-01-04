package springkotlin

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import org.junit.jupiter.api.BeforeEach

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringKotlinApplicationTest(@Autowired private var restTemplate: TestRestTemplate){

    lateinit var requestHeaders: HttpHeaders
    val URL_MOVIES = "/api/movies"

    @BeforeEach
    fun initialize() {
        requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON
    }

    @Test
    fun shouldFindAll() {

        val movieList = restTemplate.getForObject(URL_MOVIES, Array<Movie>::class.java)

        assertTrue(movieList.size > 0)
    }

    @Test
    fun shouldFindMovieByName() {
        val movie: Movie = restTemplate.getForObject(URL_MOVIES+"/search/Deadpool 2", Movie::class.java)
        assertEquals(movie.title, "Deadpool 2")
    }

    @Test
    fun shouldCreateNewMovie() {

        val body = """{"title":"test","releaseDate":"2017-12-28","watched":false}"""

        val httpEntity = HttpEntity(body, requestHeaders)

        val apiResponse = restTemplate.postForObject(URL_MOVIES, httpEntity, Map::class.java, Collections.EMPTY_MAP)

        assertEquals("test", apiResponse.get("title"))
        assertEquals("2017-12-28T00:00:00.000+0000", apiResponse.get("releaseDate"))
        assertEquals(false, apiResponse.get("watched"))
    }

    @Test
    fun shouldEdit(){

        val movie: Movie = restTemplate.getForObject(URL_MOVIES+"/search/Deadpool 2", Movie::class.java)

        movie.title = "Deadpool 3"

        val objectMaper = ObjectMapper()

        val httpEntity = HttpEntity(objectMaper.writeValueAsString(movie), requestHeaders)

        val apiResponse = restTemplate.postForObject(URL_MOVIES, httpEntity, Movie::class.java, Collections.EMPTY_MAP)

        assertEquals(movie._id, apiResponse?._id)
        assertEquals(movie.title, apiResponse?.title)
        assertEquals(movie.releaseDate, apiResponse?.releaseDate)
        assertEquals(movie.watched, apiResponse?.watched)
    }

    @Test
    fun shouldDelete(){

        var movie: Movie? = restTemplate.getForObject(URL_MOVIES+"/search/Avengers: Infinity War", Movie::class.java)

        val httpEntity = HttpEntity(Collections.EMPTY_MAP, requestHeaders)

        restTemplate.delete(URL_MOVIES+"/"+movie?._id, httpEntity,  Map::class.java, Collections.EMPTY_MAP)

        movie = restTemplate.getForObject(URL_MOVIES + "/search/Avengers: Infinity War", Movie::class.java)

        assertNull(movie)

    }
}
