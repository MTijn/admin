package nl.martijnklene.admin.config

import no.api.freemarker.java8.Java8ObjectWrapper
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer

@Configuration
class FreeMarkerConfig : BeanPostProcessor {
    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is FreeMarkerConfigurer) {
            bean.configuration.objectWrapper = Java8ObjectWrapper(freemarker.template.Configuration.VERSION_2_3_30)
        }
        return bean
    }
}
