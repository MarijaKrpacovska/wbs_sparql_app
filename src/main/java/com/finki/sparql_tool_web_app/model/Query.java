package com.finki.sparql_tool_web_app.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(schema = "sparql_tool")
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 3000)
    private String content;

    @ManyToOne
    private Endpoint endpoint;

    public Query() {
    }

    public Query(String name, String content, Endpoint endpoint) {
        this.name = name;
        this.content = content;
        this.endpoint=endpoint;
    }
}
