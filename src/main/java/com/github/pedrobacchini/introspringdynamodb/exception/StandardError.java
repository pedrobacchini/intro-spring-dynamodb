package com.github.pedrobacchini.introspringdynamodb.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class StandardError implements Serializable {

    private static final long serialVersionUID = 8985316531674257114L;

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
