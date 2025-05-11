package ru.parog.magacourseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"ru.parog.magacourseservice.entity", "ru.parog.onlinelearningplatformmodel.entity"})
@EnableJpaRepositories(basePackages = {"ru.parog.magacourseservice.repository"})
public class MagaCourseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagaCourseServiceApplication.class, args);
    }

}
