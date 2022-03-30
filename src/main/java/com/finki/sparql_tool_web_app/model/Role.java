package com.finki.sparql_tool_web_app.model;


import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_role", schema = "sparql_tool")
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(targetEntity = User.class, mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users;

    public Role() {
    }

    public Role(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

}