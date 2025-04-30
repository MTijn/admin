package nl.martijnklene.admin.http.rest

import nl.martijnklene.admin.http.rest.transformer.BlogPostsToUrlTransformer
import nl.martijnklene.admin.repository.BlogRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SitemapController(
    private val blogRepository: BlogRepository,
    private val blogPostsToUrlTransformer: BlogPostsToUrlTransformer,
) {
    @GetMapping(
        "/sitemap.xml",
        produces = ["application/xml"],
    )
    fun sitemap(): ResponseEntity<Any> {
        val blogPosts =
            blogRepository
                .findBlogPosts()
                .filter { it.publishedAt != null }
                .sortedBy { it.publishedAt }
                .asReversed()

        return ResponseEntity.ok(
            blogPostsToUrlTransformer.transFormBlogPostsToUrls(blogPosts),
        )
    }
}
