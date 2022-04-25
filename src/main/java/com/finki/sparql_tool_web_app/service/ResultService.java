package com.finki.sparql_tool_web_app.service;

import com.finki.sparql_tool_web_app.model.DTO.QueryDto;
import com.finki.sparql_tool_web_app.model.DTO.ResultDto;
import com.finki.sparql_tool_web_app.model.DTO.ResultGetDto;
import com.finki.sparql_tool_web_app.model.Result;

import java.util.List;
import java.util.Optional;

public interface ResultService {
    List<Result> findAll();

    Optional<Result> findById(Long id);

    Optional<Result> findByQueryId(Long id);

    Optional<ResultGetDto> findDtoById(Long id);

    Optional<ResultGetDto> findDtoByQueryId(Long id);

    List<String> save(ResultDto resultDto);

    public Optional<Result> save(String content, Long queryId, List<String> contentList);

    void deleteById(Long id);

}
