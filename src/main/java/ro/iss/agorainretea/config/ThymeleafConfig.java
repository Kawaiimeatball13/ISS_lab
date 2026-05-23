package ro.iss.agorainretea.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @PostConstruct
    public void extension() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\Mihus\\Mihu\\Facultate o7\\ISS\\AgoraInRetea\\src\\main\\resources\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(templateEngine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        templateEngine.addTemplateResolver(resolver);
    }
}
