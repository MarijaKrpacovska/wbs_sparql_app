package com.finki.sparql_tool_web_app.service;

import com.finki.sparql_tool_web_app.model.DTO.QueryDto;
import com.finki.sparql_tool_web_app.model.QueryInfo;

import java.util.List;
import java.util.Optional;

public interface QueryInfoService {
    List<QueryInfo> findAll();

    Optional<QueryInfo> findById(Long id);

   // Optional<Query> save(QueryDto queryDto);

    List<String> saveOld(QueryDto queryDto);

    Optional<QueryInfo> save(QueryDto queryDto);

    List<QueryInfo> findAllForUser(Long user);

    List<QueryInfo> findAllForUser(String userEmail);

    Optional<QueryInfo> edit(Long id, QueryDto queryDto);

    void deleteById(Long id);

}
