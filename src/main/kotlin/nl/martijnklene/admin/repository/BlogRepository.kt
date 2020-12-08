package nl.martijnklene.admin.repository

import nl.martijnklene.admin.entity.Blog
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.ZoneId
import java.util.*

@Repository
class BlogRepository(val jdbcTemplate: JdbcTemplate) {
    fun findBlogPosts(): Collection<Blog> {
        return this.jdbcTemplate.query(
            "select * from blog_post order by created_at"
        ) { it, _ ->
            mapResultToBlog(it)
        }
    }

    fun findSingleBlogPost(id: UUID): Blog? {
        try {
            return this.jdbcTemplate.queryForObject(
                "select * from blog_post where id = ?",
                arrayOf(id)
            ) { it, _ ->
                mapResultToBlog(it)
            }
        } catch (exception: EmptyResultDataAccessException) {
            return null
        }
    }

    private fun mapResultToBlog(it: ResultSet) = Blog(
        UUID.fromString(it.getString("id")),
        it.getString("title"),
        it.getString("content"),
        it.getString("tags"),
        it.getString("author"),
        it.getTimestamp("published_at")?.toLocalDateTime()?.atZone(ZoneId.of("Europe/Amsterdam")),
        it.getTimestamp("created_at").toLocalDateTime().atZone(ZoneId.of("Europe/Amsterdam"))
    )

    fun insert(blog: Blog) {
        jdbcTemplate.update(
            "insert into blog_post (id, title, content, tags, author, published_at, created_at) values (?, ?, ?, ?, ?, ?, ?)",
            blog.id,
            blog.title,
            blog.content,
            blog.tags,
            blog.author,
            blog.publishedAt?.toInstant(),
            blog.createdAt.toInstant()
        )
    }

    fun update(blog: Blog) {
        jdbcTemplate.update(
            "update blog_post set title = ?, content = ?, tags = ?, author = ?, published_at = ? where id = ?",
            blog.title,
            blog.content,
            blog.tags,
            blog.author,
            blog.publishedAt?.toInstant(),
            blog.id
        )
    }

    fun delete(blog: Blog) {
        jdbcTemplate.update(
            "delete from blog_post where id = ?",
            blog.id
        )
    }
}
