package com.finki.sparql_tool_web_app.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(schema = "sparql_tool")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10485760)
    private String content;

    @OneToOne
    @JoinColumn(name = "query_id",referencedColumnName = "id")
    private Query query;

    @Column(length = 10485760)
    @Convert(converter = StringListConverter.class)
    private List<String> contentList;

    public Result() {
    }

    public Result(String content, Query query, List<String> list) {
        this.content = content;
        this.query = query;
        this.contentList=list;
    }
}
