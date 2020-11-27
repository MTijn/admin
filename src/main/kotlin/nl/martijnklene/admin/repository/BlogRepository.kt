package nl.martijnklene.admin.repository

import nl.martijnklene.admin.entity.Blog
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.ZoneId
import java.util.*

@Repository
class BlogRepository(val jdbcTemplate: JdbcTemplate) {
    fun findBlogPosts(): Collection<Blog> {
        return this.jdbcTemplate.query(
            "select * from blog_post"
        ) { it, _ ->
            mapResultToBlog(it)
        }
    }

    fun findSingleBlogPost(id: UUID): Blog? {
        return this.jdbcTemplate.queryForObject(
            "select * from blog_post where id = ?"
        ) { it, _ ->
            if (it == null) {
                return@queryForObject null
            }
            mapResultToBlog(it)
        }
    }

    private fun mapResultToBlog(it: ResultSet) = Blog(
        UUID.fromString(it.getString("id")),
        it.getString("title"),
        it.getString("content"),
        it.getString("tags"),
        it.getString("author"),
        it.getTimestamp("published_at")?.toLocalDateTime()!!.atZone(ZoneId.of("UTC")),
        it.getTimestamp("created_at").toLocalDateTime().atZone(ZoneId.of("UTC"))
    )

    fun insert(blog: Blog) {
        jdbcTemplate.update(
            "insert into blog_post (id, title, content, tags, author, published_at, created_at) values (?, ?, ?, ?, ?, ?, ?)",
            blog.id,
            blog.title,
            blog.content,
            blog.tags,
            blog.author,
            blog.publishedAt?.toEpochSecond(),
            blog.createdAt.toEpochSecond()
        )
    }

    fun update(blog: Blog) {
        jdbcTemplate.update(
            "update blog_post set title = ?, content = ?, tags = ?, author = ?, published_at = ? where id = ?",
            blog.title,
            blog.content,
            blog.tags,
            blog.author,
            blog.publishedAt?.toEpochSecond(),
            blog.id
        )
    }
}
