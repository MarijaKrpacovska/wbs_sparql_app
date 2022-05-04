package com.finki.sparql_tool_web_app.model.DTO;

import lombok.Data;
import org.apache.jena.query.QueryType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class QueryDto {
    private String name;
    private String content;
    private Long endpointId;
    private String userEmail;



    public QueryDto() {
    }

    public QueryDto(String name, String content, Long endpointId, String userEmail) {
        this.name = name;
        this.content = content;
        this.endpointId=endpointId;
        this.userEmail=userEmail;
    }
}
