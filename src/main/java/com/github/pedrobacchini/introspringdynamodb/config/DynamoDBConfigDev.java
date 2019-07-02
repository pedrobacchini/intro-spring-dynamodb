package com.github.pedrobacchini.introspringdynamodb.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.github.pedrobacchini.introspringdynamodb.repository.UserRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
@EnableDynamoDBRepositories(basePackageClasses = UserRepository.class)
public class DynamoDBConfigDev {

    private final IntroSpringDynamodbProperty introSpringDynamodbProperty;

    public DynamoDBConfigDev(IntroSpringDynamodbProperty introSpringDynamodbProperty) {
        this.introSpringDynamodbProperty = introSpringDynamodbProperty;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {

        AwsClientBuilder.EndpointConfiguration clientBuilder = new AwsClientBuilder
                .EndpointConfiguration(
                introSpringDynamodbProperty.getAmazonDynamodb().getEndpoint(),
                introSpringDynamodbProperty.getAmazonDynamodb().getRegion()
        );

        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(clientBuilder)
                .build();
    }

    @Bean
    public DynamoDB dynamoDB() { return new DynamoDB(amazonDynamoDB()); }
}