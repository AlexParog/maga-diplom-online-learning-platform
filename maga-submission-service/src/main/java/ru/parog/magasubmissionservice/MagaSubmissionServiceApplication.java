package ru.parog.magasubmissionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"ru.parog.magasubmissionservice.entity", "ru.parog.onlinelearningplatformmodel.entity"})
@EnableJpaRepositories(basePackages = {"ru.parog.magasubmissionservice.repository"})
public class MagaSubmissionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagaSubmissionServiceApplication.class, args);
    }

}
