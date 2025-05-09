package nl.martijnklene.admin.http.rest.transformer

import nl.martijnklene.admin.entity.Blog
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.StringWriter
import java.time.LocalDate
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import org.w3c.dom.Document
import org.w3c.dom.Element

@Service
class BlogPostsToUrlTransformer {
    fun transFormBlogPostsToUrls(blogPosts: List<Blog>): String {
        val uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri()
        var lastPublishedAt = LocalDate.now()
        if (blogPosts.isNotEmpty()) {
            lastPublishedAt = blogPosts.first().publishedAt!!.toLocalDate()
        }

        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val document = builder.newDocument()

        val siteMapIndex = document.createElement("urlset")
        siteMapIndex.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9")
        siteMapIndex.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
        siteMapIndex.setAttribute(
            "xsi:schemaLocation",
            "http://www.sitemaps.org/schemas/sitemap/0.9  http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd",
        )
        document.appendChild(siteMapIndex)

        // Add home page URL
        createUrlElement(document, siteMapIndex, uri.scheme + "://martijnklene.nl", lastPublishedAt.toString())

        // Add archive page URL
        createUrlElement(document, siteMapIndex, uri.scheme + "://martijnklene.nl/archive", lastPublishedAt.toString())

        // Add blog post URLs
        blogPosts.forEach {
            createUrlElement(
                document,
                siteMapIndex,
                uri.scheme + "://martijnklene.nl/detail/" + it.id,
                it.publishedAt!!.toLocalDate().toString(),
            )
        }

        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")

        val source = DOMSource(document)
        val writer = StringWriter()
        val result = StreamResult(writer)

        document.xmlStandalone = true
        transformer.transform(source, result)

        return writer.toString()
    }

    /**
     * Creates a URL element for the sitemap with the specified location, last modified date, and change frequency.
     *
     * @param document The XML document
     * @param parent The parent element to which the URL element will be added
     * @param location The URL location
     * @param lastModified The last modified date
     * @param changeFreq The change frequency (defaults to "monthly")
     */
    private fun createUrlElement(
        document: Document,
        parent: Element,
        location: String,
        lastModified: String,
        changeFreq: String = "monthly",
    ) {
        val urlTag = document.createElement("url")
        parent.appendChild(urlTag)

        val locTag = document.createElement("loc")
        locTag.appendChild(document.createTextNode(location))
        urlTag.appendChild(locTag)

        val lastModTag = document.createElement("lastmod")
        lastModTag.appendChild(document.createTextNode(lastModified))
        urlTag.appendChild(lastModTag)

        val priorityTag = document.createElement("changefreq")
        priorityTag.appendChild(document.createTextNode(changeFreq))
        urlTag.appendChild(priorityTag)
    }
}
