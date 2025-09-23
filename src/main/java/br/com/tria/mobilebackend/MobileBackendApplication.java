package br.com.tria.mobilebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
public class MobileBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileBackendApplication.class, args);
    }

}
