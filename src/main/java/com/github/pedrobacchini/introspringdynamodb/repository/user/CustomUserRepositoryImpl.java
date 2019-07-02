package com.github.pedrobacchini.introspringdynamodb.repository.user;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pedrobacchini.introspringdynamodb.exception.DuplicateTableException;
import com.github.pedrobacchini.introspringdynamodb.exception.GenericDynamoDBException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

@Slf4j
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private static final String TABLE_NAME = "User";

    private final DynamoDB dynamoDB;

    public CustomUserRepositoryImpl(DynamoDB dynamoDB) { this.dynamoDB = dynamoDB; }

    @Override
    public void createTable() {
        try {
            Table table = dynamoDB.createTable(TABLE_NAME,
                    Collections.singletonList(new KeySchemaElement("id", KeyType.HASH)),
                    Collections.singletonList(new AttributeDefinition("id", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();

            log.info("Success. Table status: " + table.getDescription().getTableStatus());
        } catch(ResourceInUseException e) {
            log.error("Table already Exists: {}", e.getMessage());
            throw new DuplicateTableException(e);
        } catch(Exception e) {
            log.error("Unable to create table: {}", e.getMessage());
            throw new GenericDynamoDBException(e);
        }
    }

    @Override
    public void loadData() throws IOException {
        Table table = dynamoDB.getTable(TABLE_NAME);

        JsonParser parser = new JsonFactory().createParser(new File("users.json"));

        JsonNode rootNode = new ObjectMapper().readTree(parser);

        Iterator<JsonNode> iterator = rootNode.iterator();

        JsonNode currentNode;

        while(iterator.hasNext()) {

            currentNode = iterator.next();

            String id = currentNode.path("id").asText();
            String firstName = currentNode.path("firstName").asText();
            String lastName = currentNode.path("lastName").asText();

            try {
                table.putItem(new Item()
                        .withPrimaryKey("id", id)
                        .withString("firstName", firstName)
                        .withString("lastName", lastName));

                log.info("PutItem succeeded {} {} {} ", id, firstName, lastName);
            } catch(Exception e) {
                log.error("Unable to add hotel: {} - {}: \n{}", id, firstName, e.getMessage());
                break;
            }
        }
        parser.close();
    }
}
