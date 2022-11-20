package uz.bakhromjon;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "uz.darico")
public class OutGoingApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutGoingApplication.class, args);
    }
}
