package nl.martijnklene.admin.http.rest.dto

import com.fasterxml.jackson.annotation.JsonRootName
import java.time.LocalDate

@JsonRootName("url")
data class Url(
    val loc: String,
    val lastmod: LocalDate,
    val changefreq: String,
)
