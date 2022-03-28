package com.finki.sparql_tool_web_app.model.DTO;

import lombok.Data;

@Data
public class EndpointDto {
    private String name;

    private String url;

    public EndpointDto() {
    }

    public EndpointDto(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
