package springkotlin

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
class Movie(
        var title: String = "",
        var releaseDate: Date? = null,
        var watched: Boolean = false
)