package com.finki.sparql_tool_web_app.service;

import com.finki.sparql_tool_web_app.model.Result;

import java.util.List;
import java.util.Optional;

public interface ResultService {
    List<Result> findAll();

    Optional<Result> findById(Long id);

    void deleteById(Long id);

}
