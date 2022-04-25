package com.finki.sparql_tool_web_app.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(schema = "sparql_tool")
public class QueryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 3000)
    private String content;

    @ManyToOne
    private Endpoint endpoint;
    @ManyToOne
    private User user;

    private LocalDateTime timestamp;

    private Long queryResultId;

    private String uniqueUrl;

    public QueryInfo() {
    }

    public QueryInfo(String name, String content, Endpoint endpoint, User user, LocalDateTime timestamp) {
        this.name = name;
        this.content = content;
        this.endpoint=endpoint;
        this.user = user;
        this.timestamp=timestamp;
    }

}
