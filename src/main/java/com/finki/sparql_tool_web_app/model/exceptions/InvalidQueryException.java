package com.finki.sparql_tool_web_app.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidQueryException extends RuntimeException {
    public InvalidQueryException(){
        super(String.format("Error executing query"));
    }
}
