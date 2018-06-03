package bricks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Creates a standalone application
 */
//@SpringBootApplication
//@ComponentScan({"bricks"})                                        // make Components scan in the packages pointed in
////@ComponentScan(basePackageClasses = CustomerOrderController.class)  // make scan exactly for CustomerOrderController

@SpringBootApplication
@EntityScan(basePackages = {"bricks", "bricks.repo", "bricks.controller"})
//@EnableJpaRepositories(basePackages = {"bricks"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
