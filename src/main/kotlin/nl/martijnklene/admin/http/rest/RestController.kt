package nl.martijnklene.admin.http.rest

import nl.martijnklene.admin.entity.Blog
import nl.martijnklene.admin.repository.BlogRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class RestController(private val blogRepository: BlogRepository) {
    @GetMapping("v1/blog-posts")
    fun returnAll(): ResponseEntity<Collection<Blog>> {
        return ResponseEntity.ok().body(this.blogRepository.findBlogPosts().filter { it.publishedAt != null })
    }

    @GetMapping("v1/blog-posts/{id}")
    fun singleBlogPost(@PathVariable id: String): ResponseEntity<Any> {
        val blogPost = this.blogRepository.findSingleBlogPost(UUID.fromString(id))
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(blogPost)
    }

    @GetMapping("v1/blog-posts/last")
    fun lastPublishedBlog(): ResponseEntity<Any> {
        val blogPosts = this.blogRepository.findBlogPosts().filter {
            it.publishedAt != null
        }.sortedBy { it.createdAt }.asReversed().firstOrNull()

        return ResponseEntity.ok().body(blogPosts)
    }
}
