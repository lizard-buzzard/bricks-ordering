package bricks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"bricks", "bricks.repo"}) // create CustomerOrderRepository instance
public class ApplicationConfiguration {
}
