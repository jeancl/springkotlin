package springkotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


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

    @Test
    fun shouldCreateNewMovie() {

        val url = "/api/movies"
        val body = """{"title":"test","releaseDate":"2017-12-28","watched":false}"""
        val assertContent = "{title=test, releaseDate=2017-12-28T00:00:00.000+0000, watched=false}"

        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        val httpEntity = HttpEntity(body, requestHeaders)

        val apiResponse = restTemplate.postForObject(url, httpEntity, Map::class.java, Collections.EMPTY_MAP)

        assertEquals(assertContent, apiResponse.toString())
    }
}
