package com.github.pedrobacchini.introspringdynamodb;

import com.github.pedrobacchini.introspringdynamodb.config.IntroSpringDynamodbProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(IntroSpringDynamodbProperty.class)
public class IntroSpringDynamodbApplication {

    public static void main(String[] args) { SpringApplication.run(IntroSpringDynamodbApplication.class, args); }

}
