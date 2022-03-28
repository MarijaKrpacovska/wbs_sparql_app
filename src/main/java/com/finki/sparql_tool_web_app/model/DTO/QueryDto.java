package com.finki.sparql_tool_web_app.model.DTO;

import lombok.Data;

@Data
public class QueryDto {
    private String name;
    private String content;
    private Long endpointId;

    public QueryDto() {
    }

    public QueryDto(String name, String content, Long endpointId) {
        this.name = name;
        this.content = content;
        this.endpointId=endpointId;
    }
}
