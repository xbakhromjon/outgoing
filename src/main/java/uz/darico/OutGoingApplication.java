package uz.darico;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "uz.darico")
public class OutGoingApplication {
    public static void main(String[] args) {

        SpringApplication.run(OutGoingApplication.class, args);
    }
}
