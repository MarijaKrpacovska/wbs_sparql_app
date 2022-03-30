package com.finki.sparql_tool_web_app.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String email;
    private List<String> roles;
    private String userId;
}
