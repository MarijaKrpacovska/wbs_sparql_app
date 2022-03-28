package com.finki.sparql_tool_web_app.service.impl;

import com.finki.sparql_tool_web_app.model.DTO.EndpointDto;
import com.finki.sparql_tool_web_app.model.Endpoint;
import com.finki.sparql_tool_web_app.model.exceptions.EndpointNotFoundException;
import com.finki.sparql_tool_web_app.repository.EndpointRepository;
import com.finki.sparql_tool_web_app.service.EndpointService;
import com.finki.sparql_tool_web_app.repository.EndpointRepository;
import com.finki.sparql_tool_web_app.service.EndpointService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EndpointServiceImpl implements EndpointService {

    private final EndpointRepository endpointRepository;

    public EndpointServiceImpl(EndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
    }

    @Override
    public List<Endpoint> findAll() {
        return endpointRepository.findAll();
    }

    @Override
    public Optional<Endpoint> findById(Long id) {
        return endpointRepository.findById(id);
    }

    @Override
    public Optional<Endpoint> save(EndpointDto endpointDto) {
        Endpoint endpoint = new Endpoint(endpointDto.getName(),endpointDto.getUrl());
        return Optional.of(endpointRepository.save(endpoint));
    }

    @Override
    public Optional<Endpoint> edit(Long id, EndpointDto endpointDto) {
        Endpoint endpoint = this.findById(id).orElseThrow(() -> new EndpointNotFoundException(id));
        endpoint.setName(endpointDto.getName());
        endpoint.setUrl(endpointDto.getUrl());
        endpointRepository.save(endpoint);
        return Optional.of(endpoint);
    }

    @Override
    public void deleteById(Long id) {
        this.endpointRepository.deleteById(id);
    }
}
