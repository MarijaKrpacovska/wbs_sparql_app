package com.finki.sparql_tool_web_app.service.impl;

import com.finki.sparql_tool_web_app.model.DTO.ResultDto;
import com.finki.sparql_tool_web_app.model.Query;
import com.finki.sparql_tool_web_app.model.Result;
import com.finki.sparql_tool_web_app.model.exceptions.QueryNotFoundException;
import com.finki.sparql_tool_web_app.repository.QueryRepository;
import com.finki.sparql_tool_web_app.repository.ResultRepository;
import com.finki.sparql_tool_web_app.service.ResultService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final QueryRepository queryRepository;

    public ResultServiceImpl(ResultRepository resultRepository, QueryRepository queryRepository) {
        this.resultRepository = resultRepository;
        this.queryRepository = queryRepository;
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
    public Optional<Result> findByQueryId(Long id) {
        Query query = queryRepository.getById(id);
        Result result = this.resultRepository.findByQuery(query);
        return Optional.of(result);
    }

    @Override
    public List<String> save(ResultDto resultDto) {
        Query query = queryRepository.findById(resultDto.getQueryId()).orElseThrow(()-> new QueryNotFoundException(resultDto.getQueryId()));
        //Result result = new Result(resultDto.getResultSet(),query.getId());
      //  return Optional.of(endpointRepository.save(endpoint));
        return null;
    }

    @Override
    public Optional<Result> save(String content, Long queryId, List<String> contentList) {
        Result result = new Result(content,queryRepository.getById(queryId),contentList);
        return Optional.of(this.resultRepository.save(result));
    }

    @Override
    public void deleteById(Long id) {
        this.resultRepository.deleteById(id);
    }
}
