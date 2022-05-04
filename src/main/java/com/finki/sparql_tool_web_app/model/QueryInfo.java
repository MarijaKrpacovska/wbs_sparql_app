package com.finki.sparql_tool_web_app.model;

import lombok.Data;
import org.apache.jena.query.QueryType;

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

    @Enumerated(EnumType.STRING)
    QueryType queryType;

    private Boolean valid;

    public QueryInfo() {
    }

    public QueryInfo(String name, String content, Endpoint endpoint, User user, LocalDateTime timestamp, Boolean valid) {
        this.name = name;
        this.content = content;
        this.endpoint=endpoint;
        this.user = user;
        this.timestamp=timestamp;
        this.valid=valid;
    }

}
