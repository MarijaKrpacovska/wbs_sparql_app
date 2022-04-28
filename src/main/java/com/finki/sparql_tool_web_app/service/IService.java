package com.finki.sparql_tool_web_app.service;

import com.finki.sparql_tool_web_app.model.DTO.UserRegisterDto;
import com.finki.sparql_tool_web_app.model.exceptions.InvalidTokenException;

import java.util.Collection;
import java.util.Optional;

public interface IService<T> {
    Collection<T> findAll();

    Optional<T> findById(Long id);

    Optional<T> saveOrUpdate(T t);

    Optional<T> register(UserRegisterDto userRegisterDto);

    public boolean verifyUser(String token) throws InvalidTokenException;

    String deleteById(Long id);
}