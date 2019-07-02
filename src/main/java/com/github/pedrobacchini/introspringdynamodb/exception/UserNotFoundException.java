package com.github.pedrobacchini.introspringdynamodb.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Exception e) { super(e); }
}
