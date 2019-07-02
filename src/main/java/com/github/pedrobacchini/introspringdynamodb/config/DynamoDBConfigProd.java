package com.github.pedrobacchini.introspringdynamodb.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.github.pedrobacchini.introspringdynamodb.repository.UserRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
@EnableDynamoDBRepositories(basePackageClasses = UserRepository.class)
public class DynamoDBConfigProd {

    private final IntroSpringDynamodbProperty introSpringDynamodbProperty;

    public DynamoDBConfigProd(IntroSpringDynamodbProperty introSpringDynamodbProperty) {
        this.introSpringDynamodbProperty = introSpringDynamodbProperty;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        System.out.println(introSpringDynamodbProperty.getAmazonDynamodb().getAccesskey());
        System.out.println(introSpringDynamodbProperty.getAmazonDynamodb().getSecretkey());
        return new BasicAWSCredentials(
                introSpringDynamodbProperty.getAmazonDynamodb().getAccesskey(),
                introSpringDynamodbProperty.getAmazonDynamodb().getSecretkey()
        );
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(amazonAWSCredentialsProvider())
                .withRegion(introSpringDynamodbProperty.getAmazonDynamodb().getRegion())
                .build();
    }

    @Bean
    public DynamoDB dynamoDB() { return new DynamoDB(amazonDynamoDB()); }

    private AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }
}
