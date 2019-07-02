package com.github.pedrobacchini.introspringdynamodb.exception;

public class InvalidLoadingDataFile extends RuntimeException {
    public InvalidLoadingDataFile(Exception e) { super(e); }
}
