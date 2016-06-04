package configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@EnableAutoConfiguration
@ComponentScan({ "com.registration.service, com.registration.dao, com.registration.controller" })
@Configuration
@ImportResource("classpath:bean.xml")
@TestPropertySource(locations="classpath:application.yml")
public class SpringTestConfiguration {
}
