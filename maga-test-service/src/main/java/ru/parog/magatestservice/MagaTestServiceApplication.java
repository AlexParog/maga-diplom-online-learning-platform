package ru.parog.magatestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"ru.parog.magatestservice.entity", "ru.parog.onlinelearningplatformmodel.entity"})
@EnableJpaRepositories(basePackages = {"ru.parog.magatestservice.repository"})
public class MagaTestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagaTestServiceApplication.class, args);
    }

}
