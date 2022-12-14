package uz.bakhromjon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : Bakhromjon Khasanboyev
 **/

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowCredentials(true).
                allowedOrigins("http://localhost:3000/").
                allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH").
                allowedHeaders("*");
    }
}
