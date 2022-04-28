package com.finki.sparql_tool_web_app.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PasswordsDontMatchException extends RuntimeException {
    public PasswordsDontMatchException(){
        super("Passwords don't match");
    }
}
