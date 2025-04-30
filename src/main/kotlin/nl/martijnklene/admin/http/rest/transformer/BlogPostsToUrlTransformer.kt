package nl.martijnklene.admin.http.rest.transformer

import nl.martijnklene.admin.entity.Blog
import nl.martijnklene.admin.http.rest.dto.Url
import nl.martijnklene.admin.http.rest.dto.Urls
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.LocalDate

@Service
class BlogPostsToUrlTransformer {
    fun transFormBlogPostsToUrls(blogPosts: List<Blog>): Urls {
        val uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri()
        var lastPublishedAt = LocalDate.now()
        if (blogPosts.isNotEmpty()) {
            lastPublishedAt = blogPosts.first().publishedAt!!.toLocalDate()
        }

        val urls = mutableListOf<Url>()
        urls.add(
            Url(
                loc = uri.scheme + "://" + uri.authority,
                lastmod = lastPublishedAt,
                changefreq = "monthly",
            ),
        )
        urls.add(
            Url(
                loc = uri.scheme + "://" + uri.authority + "/archive",
                lastmod = lastPublishedAt,
                changefreq = "monthly",
            ),
        )
        blogPosts.map {
            urls.add(
                Url(
                    loc = uri.scheme + "://" + uri.authority + "/detail/" + it.id,
                    lastmod = it.publishedAt!!.toLocalDate(),
                    changefreq = "monthly",
                ),
            )
        }

        return Urls(urls)
    }
}
