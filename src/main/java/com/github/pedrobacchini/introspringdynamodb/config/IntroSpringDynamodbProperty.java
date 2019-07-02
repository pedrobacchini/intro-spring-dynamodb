package com.github.pedrobacchini.introspringdynamodb.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring-dynamodb")
public class IntroSpringDynamodbProperty {

    @Getter
    private final AmazonDynamoDB amazonDynamodb = new AmazonDynamoDB();

    @Getter
    @Setter
    @SuppressWarnings("WeakerAccess")
    public static class AmazonDynamoDB {
        private String endpoint;
        private String region;
        private String accesskey;
        private String secretkey;
    }
}
