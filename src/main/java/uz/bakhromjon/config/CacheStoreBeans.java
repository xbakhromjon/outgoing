package uz.bakhromjon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.bakhromjon.missive.Missive;

import java.util.concurrent.TimeUnit;

/**
 * @author : Bakhromjon Khasanboyev
 **/
@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Missive> employeeCache() {
        return new CacheStore<Missive>(10, TimeUnit.MINUTES);
    }

}
