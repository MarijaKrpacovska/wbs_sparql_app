package com.finki.sparql_tool_web_app.service;

import com.finki.sparql_tool_web_app.model.DTO.EndpointDto;
import com.finki.sparql_tool_web_app.model.Endpoint;

import java.util.List;
import java.util.Optional;

public interface EndpointService {
    List<Endpoint> findAll();

    Optional<Endpoint> findById(Long id);

    Optional<Endpoint> save(EndpointDto endpointDto);

    Optional<Endpoint> edit(Long id, EndpointDto endpointDto);

    void deleteById(Long id);

}
