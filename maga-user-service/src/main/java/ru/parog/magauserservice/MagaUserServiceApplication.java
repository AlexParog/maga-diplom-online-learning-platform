package ru.parog.magauserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"ru.parog.magauserservice.entity", "ru.parog.onlinelearningplatformmodel.entity"})
@EnableJpaRepositories(basePackages = {"ru.parog.magauserservice.repository"})
public class MagaUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagaUserServiceApplication.class, args);
    }

}
