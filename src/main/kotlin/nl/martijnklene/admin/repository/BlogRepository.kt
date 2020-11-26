package nl.martijnklene.admin.repository

import nl.martijnklene.admin.entity.Blog
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.ZoneId
import java.util.*

@Repository
class BlogRepository(val jdbcTemplate: JdbcTemplate) {
    fun findBlogPosts(): Collection<Blog> {
        return this.jdbcTemplate.query(
            "select * from blog_post"
        ) { it, _ ->
            Blog(
                UUID.fromString(it.getString("id")),
                it.getString("title"),
                it.getString("content"),
                it.getString("tags"),
                it.getString("author"),
                it.getTimestamp("published_at").toLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam")),
                it.getTimestamp("created_at").toLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam"))
            )
        }
    }
}
