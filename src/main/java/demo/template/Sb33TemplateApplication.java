package demo.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaAuditing
@SpringBootApplication
public class Sb33TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sb33TemplateApplication.class, args);
    }

}
