package com.finki.sparql_tool_web_app.service;

import com.finki.sparql_tool_web_app.model.DTO.QueryDto;
import com.finki.sparql_tool_web_app.model.Query;

import java.util.List;
import java.util.Optional;

public interface QueryService {
    List<Query> findAll();

    Optional<Query> findById(Long id);

   // Optional<Query> save(QueryDto queryDto);

    List<String> save(QueryDto queryDto);

    Optional<Query> edit(Long id, QueryDto queryDto);

    void deleteById(Long id);

}
