package nl.martijnklene.admin.http.model

data class BlogPostForm(
    val title: String,
    val content: String,
    val tags: String,
    val published: Boolean = false,
)
