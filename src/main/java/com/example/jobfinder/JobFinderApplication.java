package com.example.jobfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages={"com.*"})
@EntityScan( basePackages = {"com.*"} )
@EnableJpaRepositories( basePackages = {"com.*"} )
public class JobFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobFinderApplication.class, args);
    }

}
