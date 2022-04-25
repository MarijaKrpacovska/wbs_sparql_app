package com.finki.sparql_tool_web_app.model.DTO;

import com.finki.sparql_tool_web_app.model.QueryInfo;
import com.finki.sparql_tool_web_app.model.converters.StringListConverter;
import lombok.Data;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.List;

@Data
public class ResultGetDto {
    private Long id;
    private String content;
    private QueryInfo queryInfo;
    private List<String> contentList;
    private JSONObject jsonContent;


    public ResultGetDto() {
    }

    public ResultGetDto(Long id, String content, QueryInfo queryInfo, List<String> contentList, JSONObject jsonContent) {
        this.id = id;
        this.content = content;
        this.queryInfo = queryInfo;
        this.contentList = contentList;
        this.jsonContent = jsonContent;
    }
}
