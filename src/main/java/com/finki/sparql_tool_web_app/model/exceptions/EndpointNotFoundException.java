package com.finki.sparql_tool_web_app.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EndpointNotFoundException extends RuntimeException {
    public EndpointNotFoundException (Long id){
        super(String.format("Endpoint with id %d was not found",id));
    }
}
