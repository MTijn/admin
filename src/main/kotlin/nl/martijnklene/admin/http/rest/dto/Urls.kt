package nl.martijnklene.admin.http.rest.dto

import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper

@JsonRootName("urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
class Urls(
    @JacksonXmlElementWrapper(useWrapping = false)
    val url: List<Url>,
)
