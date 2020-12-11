package nl.martijnklene.admin.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.util.*

internal class BlogTest {
    @Test
    fun `data stays the same`() {
        val uuid = UUID.randomUUID()
        val dateTime = ZonedDateTime.now()
        val blog = Blog(
            uuid,
            "title",
            "content",
            "tags",
            "Martijn",
            dateTime,
            dateTime
        )

        assertSame(uuid, blog.id)
        assertEquals("title", blog.title)
        assertEquals("content", blog.content)
        assertEquals("tags", blog.tags)
        assertEquals("Martijn", blog.author)
        assertSame(dateTime, blog.publishedAt)
        assertSame(dateTime, blog.createdAt)
    }
}
