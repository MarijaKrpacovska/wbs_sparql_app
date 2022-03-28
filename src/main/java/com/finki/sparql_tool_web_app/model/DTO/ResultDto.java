package com.finki.sparql_tool_web_app.model.DTO;

import lombok.Data;
import org.apache.jena.query.ResultSet;

@Data
public class ResultDto {
    private ResultSet resultSet;
    private Long queryId;

    public ResultDto() {
    }

    public ResultDto(Long queryId, ResultSet resultSet) {
        this.queryId = queryId;
        this.resultSet=resultSet;
    }
}
