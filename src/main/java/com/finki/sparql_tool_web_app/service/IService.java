package com.finki.sparql_tool_web_app.service;

import java.util.Collection;
import java.util.Optional;

public interface IService<T> {
    Collection<T> findAll();

    Optional<T> findById(Long id);

    Optional<T> saveOrUpdate(T t);

    String deleteById(Long id);
}