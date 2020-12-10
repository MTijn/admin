package nl.martijnklene.admin.repository

import nl.martijnklene.admin.entity.Blog
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.ZoneId
import java.util.*

@Repository
class BlogRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun findBlogPosts(): Collection<Blog> {
        return this.jdbcTemplate.query(
            "select * from blog_post order by created_at"
        ) { it, _ ->
            mapResultToBlog(it)
        }
    }

    fun findSingleBlogPost(id: UUID): Blog? {
        val parameterSource = MapSqlParameterSource().addValue("id", id)
        return this.jdbcTemplate.query(
            "select * from blog_post where id = :id",
            parameterSource
        ) { it, _ ->
            return@query mapResultToBlog(it)
        }.singleOrNull()
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
        val parameterSource = MapSqlParameterSource()
            .addValue("id", blog.id)
            .addValue("title", blog.title)
            .addValue("content", blog.content)
            .addValue("tags", blog.tags)
            .addValue("author", blog.author)
            .addValue("published_at", blog.publishedAt?.toOffsetDateTime())
            .addValue("created_at", blog.createdAt.toOffsetDateTime())

        jdbcTemplate.update(
            "insert into blog_post (id, title, content, tags, author, published_at, created_at)" +
                    " values (:id, :title, :content, :tags, :author, :published_at, :created_at)",
            parameterSource
        )
    }

    fun update(blog: Blog) {
        val parameterSource = MapSqlParameterSource()
            .addValue("title", blog.title)
            .addValue("content", blog.content)
            .addValue("tags", blog.tags)
            .addValue("published_at", blog.publishedAt?.toOffsetDateTime())
            .addValue("id", blog.id)
        jdbcTemplate.update(
            "update blog_post set title = :title, content = :content, tags = :tags, published_at = :published_at where id = :id",
            parameterSource
        )
    }

    fun delete(blog: Blog) {
        val parameterSource = MapSqlParameterSource().addValue("id", blog.id)
        jdbcTemplate.update(
            "delete from blog_post where id = :id",
            parameterSource
        )
    }
}
