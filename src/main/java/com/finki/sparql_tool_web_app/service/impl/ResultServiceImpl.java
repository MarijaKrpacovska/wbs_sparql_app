package com.finki.sparql_tool_web_app.service.impl;

import com.finki.sparql_tool_web_app.model.Result;
import com.finki.sparql_tool_web_app.repository.ResultRepository;
import com.finki.sparql_tool_web_app.service.ResultService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    public ResultServiceImpl(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }

    @Override
    public Optional<Result> findById(Long id) {
        return resultRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.resultRepository.deleteById(id);
    }
}
