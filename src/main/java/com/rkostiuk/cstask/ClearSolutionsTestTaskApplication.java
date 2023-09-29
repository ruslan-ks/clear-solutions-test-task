package com.rkostiuk.cstask;

import com.rkostiuk.cstask.config.UserValidationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(UserValidationProperties.class)
@SpringBootApplication
public class ClearSolutionsTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClearSolutionsTestTaskApplication.class, args);
    }

}
