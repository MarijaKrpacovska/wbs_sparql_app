package com.finki.sparql_tool_web_app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_user", schema = "sparql_tool")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String mobile;
    @NotNull
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private Boolean accountVerified;

    public User(String name, String email, String mobile, String password, Role role) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setAccountVerified(Boolean verified) {
        this.accountVerified = verified;
    }

    public Boolean isAccountVerified(){
        return accountVerified;
    }

}