package com.github.pedrobacchini.introspringdynamodb.exception;

public class GenericDynamoDBException extends RuntimeException {
    public GenericDynamoDBException(Exception e) { super(e); }
}
