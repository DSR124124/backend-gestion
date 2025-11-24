package com.nettalco.gestion.backendgestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class BackendGestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendGestionApplication.class, args);
    }

}
