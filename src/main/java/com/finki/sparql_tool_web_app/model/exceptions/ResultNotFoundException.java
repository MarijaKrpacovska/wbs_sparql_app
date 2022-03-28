package com.finki.sparql_tool_web_app.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResultNotFoundException extends RuntimeException {
    public ResultNotFoundException(Long id){
        super(String.format("Result with id %d was not found",id));
    }
}
