package uz.darico.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : Bakhromjon Khasanboyev
 * @username: @xbakhromjon
 * @since : 01/10/22, Sat, 15:49
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("Access-Control-Allow-Origin=*").allowedMethods("Access-Control-Allow-Methods=POST, GET, DELETE, PATCH, PUT, OPTION").
                allowCredentials(true);
    }
}
