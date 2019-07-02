package com.github.pedrobacchini.introspringdynamodb.exception;

public class DuplicateTableException extends RuntimeException {
    public DuplicateTableException(Exception e) { super(e); }
}
