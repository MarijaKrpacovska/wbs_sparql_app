package com.finki.sparql_tool_web_app.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(schema = "sparql_tool")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3000)
    private String content;

    @OneToOne
    @JoinColumn(name = "query_id",referencedColumnName = "id")
    private Query query;

    public Result() {
    }
}
