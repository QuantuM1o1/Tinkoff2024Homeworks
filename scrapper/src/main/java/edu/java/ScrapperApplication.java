package edu.java;

import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.GithubClientConfig;
import edu.java.configuration.ResourcesConfig;
import edu.java.configuration.StackOverflowClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableConfigurationProperties({
    ApplicationConfig.class,
    ResourcesConfig.class,
    GithubClientConfig.class,
    StackOverflowClientConfig.class
})
public class ScrapperApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }
}
