package nl.martijnklene.admin.http.rest

import nl.martijnklene.admin.entity.Blog
import nl.martijnklene.admin.repository.BlogRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController(private val blogRepository: BlogRepository) {
    @GetMapping("v1/blog-posts")
    fun returnAll(): Collection<Blog> {
        return this.blogRepository.findBlogPosts()
    }
}
