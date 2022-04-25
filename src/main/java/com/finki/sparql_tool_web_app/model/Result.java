package com.finki.sparql_tool_web_app.model;

import com.finki.sparql_tool_web_app.model.converters.StringListConverter;
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
    @JoinColumn(name = "query_info_id",referencedColumnName = "id")
    private QueryInfo queryInfo;

    @Column(length = 10485760)
    @Convert(converter = StringListConverter.class)
    private List<String> contentList;

    public Result() {
    }

    public Result(String content, QueryInfo queryInfo, List<String> list) {
        this.content = content;
        this.queryInfo = queryInfo;
        this.contentList=list;
    }
}
