package springkotlin

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
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
import java.util.Locale
import java.text.SimpleDateFormat

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringKotlinApplicationTest(@Autowired private var restTemplate: TestRestTemplate){

    @Test
    fun shouldFindAll() {

        val movieList = restTemplate.getForObject("/api/movies", Array<Movie>::class.java)

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        assertEquals("Avengers: Infinity War", movieList?.get(0)?.title)
        assertEquals(format.parse("04/05/2018"), movieList?.get(0)?.releaseDate)
        assertEquals(false, movieList?.get(0)?.watched)

        assertEquals("Deadpool 2", movieList?.get(1)?.title)
        assertEquals(format.parse("01/06/2018"), movieList?.get(1)?.releaseDate)
        assertEquals(false, movieList?.get(1)?.watched)

        assertEquals("Solo: A Star Wars Story", movieList?.get(2)?.title)
        assertEquals(format.parse("25/05/2018"), movieList?.get(2)?.releaseDate)
        assertEquals(false, movieList?.get(2)?.watched)

    }

    @Test
    fun shouldFindMovieByName() {
        val movie: Movie = restTemplate.getForObject("/api/movies/search/Deadpool 2", Movie::class.java)
        assertEquals(movie.title, "Deadpool 2")
    }

    @Test
    fun shouldCreateNewMovie() {

        val url = "/api/movies"
        val body = """{"title":"test","releaseDate":"2017-12-28","watched":false}"""

        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        val httpEntity = HttpEntity(body, requestHeaders)

        val apiResponse = restTemplate.postForObject(url, httpEntity, Map::class.java, Collections.EMPTY_MAP)

        assertEquals("test", apiResponse.get("title"))
        assertEquals("2017-12-28T00:00:00.000+0000", apiResponse.get("releaseDate"))
        assertEquals(false, apiResponse.get("watched"))
    }

    @Test
    fun shouldEdit(){
        val url = "/api/movies"

        val movie: Movie = restTemplate.getForObject("/api/movies/search/Deadpool 2", Movie::class.java)

        movie.title = "Deadpool 3"

        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        val objectMaper = ObjectMapper()

        val httpEntity = HttpEntity(objectMaper.writeValueAsString(movie), requestHeaders)

        val apiResponse = restTemplate.postForObject(url, httpEntity, Movie::class.java, Collections.EMPTY_MAP)

        assertEquals(movie._id, apiResponse?._id)
        assertEquals(movie.title, apiResponse?.title)
        assertEquals(movie.releaseDate, apiResponse?.releaseDate)
        assertEquals(movie.watched, apiResponse?.watched)
    }

}
