package nl.martijnklene.admin.entity

import java.time.ZonedDateTime
import java.util.UUID

data class Blog(
    val id: UUID,
    val title: String,
    val content: String,
    val tags: String,
    val author: String,
    val publishedAt: ZonedDateTime?,
    val createdAt: ZonedDateTime,
)
