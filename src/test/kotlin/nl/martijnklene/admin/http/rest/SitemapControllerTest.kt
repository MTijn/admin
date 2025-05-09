package nl.martijnklene.admin.http.rest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath

@SpringBootTest
@AutoConfigureMockMvc
class SitemapControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `sitemap should include proper namespace declaration`() {
        val result =
            mockMvc
                .perform(get("/sitemap.xml"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
                .andExpect(xpath("/urlset").exists())
                .andExpect(xpath("/urlset/url").exists())
                .andReturn()

        val content = result.response.contentAsString
        assert(content.contains("xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"")) {
            "Namespace declaration not found in XML: $content"
        }
    }
}
