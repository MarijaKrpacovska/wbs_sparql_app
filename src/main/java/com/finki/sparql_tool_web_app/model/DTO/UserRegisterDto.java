package com.finki.sparql_tool_web_app.model.DTO;

import com.finki.sparql_tool_web_app.model.Role;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterDto {
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String repeatPassword;
    private Long roleId;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String name, String email, String mobile, String password, String repeatPassword, Long roleId) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.roleId = roleId;
    }
}
