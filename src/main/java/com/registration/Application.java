package com.registration;


import com.registration.controller.RegistrationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;

@EnableAutoConfiguration
@SpringBootApplication
@Configuration
@ComponentScan(basePackageClasses = RegistrationController.class)
@EnableJms
@ImportResource("classpath:bean.xml")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
