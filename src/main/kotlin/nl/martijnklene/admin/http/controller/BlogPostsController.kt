package nl.martijnklene.admin.http.controller

import nl.martijnklene.admin.entity.Blog
import nl.martijnklene.admin.http.model.BlogPostForm
import nl.martijnklene.admin.repository.BlogRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.view.RedirectView
import java.time.ZonedDateTime
import java.util.*

@Controller
class BlogPostsController(
    private val blogRepository: BlogRepository
) {
    @GetMapping("/blog-posts")
    fun overview(modelMap: ModelMap): String {
        modelMap.addAttribute("blogPosts", blogRepository.findBlogPosts())
        return "blog-posts"
    }

    @GetMapping("/blog-posts/new")
    fun new(): String {
        return "blog-form"
    }

    @GetMapping("/blog-posts/edit/{identifier}")
    fun form(@PathVariable identifier: String, modelMap: ModelMap): Any {
        val blogPost = blogRepository.findSingleBlogPost(UUID.fromString(identifier)) ?: return RedirectView("/")
        modelMap.addAttribute("blogPost", blogPost)
        return "blog-form"
    }

    @PostMapping("/blog-posts")
    fun post(@ModelAttribute form: BlogPostForm): RedirectView {
        val blogPost = Blog(
            UUID.randomUUID(),
            form.title,
            form.content,
            form.tags,
            "Martijn",
            if (form.published) ZonedDateTime.now() else null,
            ZonedDateTime.now()
        )
        blogRepository.insert(blogPost)
        return RedirectView("/blog-posts")
    }

    @DeleteMapping("/blog-posts/{identifier}")
    fun delete(@PathVariable identifier: String): ResponseEntity<Any> {
        val blogPost = blogRepository.findSingleBlogPost(UUID.fromString(identifier)) ?: return ResponseEntity.notFound().build()

        blogRepository.delete(blogPost)
        return ResponseEntity.noContent().build()
    }
}
