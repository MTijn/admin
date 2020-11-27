package nl.martijnklene.admin.http.rest

import nl.martijnklene.admin.entity.Blog
import nl.martijnklene.admin.repository.BlogRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class RestController(private val blogRepository: BlogRepository) {
    @GetMapping("v1/blog-posts")
    fun returnAll(): Collection<Blog> {
        return this.blogRepository.findBlogPosts()
    }

    @GetMapping("v1/blog-posts/{id}")
    fun singleBlogPost(@PathVariable id: String): Blog? {
        return this.blogRepository.findSingleBlogPost(UUID.fromString(id))
    }
}
