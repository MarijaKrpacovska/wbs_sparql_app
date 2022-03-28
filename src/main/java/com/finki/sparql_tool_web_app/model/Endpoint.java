package com.finki.sparql_tool_web_app.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(schema = "sparql_tool")
public class Endpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    public Endpoint() {
    }

    public Endpoint(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
