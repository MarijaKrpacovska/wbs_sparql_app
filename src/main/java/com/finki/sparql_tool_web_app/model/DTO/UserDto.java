package com.finki.sparql_tool_web_app.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String email;
    private List<String> roles;
    private String userId;

    public UserDto() {
    }

    public UserDto(String username, String email, List<String> roles, String userId) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.userId = userId;
    }
}
