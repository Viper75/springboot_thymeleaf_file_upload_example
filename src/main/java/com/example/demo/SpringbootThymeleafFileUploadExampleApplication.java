package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringbootThymeleafFileUploadExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootThymeleafFileUploadExampleApplication.class, args);
    }

}
