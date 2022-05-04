package com.finki.sparql_tool_web_app.service.impl;

import com.finki.sparql_tool_web_app.model.DTO.ResultDto;
import com.finki.sparql_tool_web_app.model.DTO.ResultGetDto;
import com.finki.sparql_tool_web_app.model.QueryInfo;
import com.finki.sparql_tool_web_app.model.Result;
import com.finki.sparql_tool_web_app.model.converters.XmlToJsonConverter;
import com.finki.sparql_tool_web_app.model.exceptions.QueryNotFoundException;
import com.finki.sparql_tool_web_app.model.exceptions.ResultNotFoundException;
import com.finki.sparql_tool_web_app.repository.QueryInfoRepository;
import com.finki.sparql_tool_web_app.repository.ResultRepository;
import com.finki.sparql_tool_web_app.service.ResultService;
import org.apache.jena.query.QueryType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final QueryInfoRepository queryInfoRepository;

    public ResultServiceImpl(ResultRepository resultRepository, QueryInfoRepository queryInfoRepository) {
        this.resultRepository = resultRepository;
        this.queryInfoRepository = queryInfoRepository;
    }

    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }

    @Override
    public Optional<Result> findById(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new ResultNotFoundException(id));
        String xmlContent = result.getContent();
        String jsonContent = XmlToJsonConverter.convert(xmlContent);
        result.setContent(jsonContent);
        return Optional.of(result);
    }

    @Override
    public Optional<Result> findByQueryId(Long id) {
        QueryInfo queryInfo = queryInfoRepository.getById(id);
        Result result = this.resultRepository.findByQueryInfo(queryInfo);
        String xmlContent = result.getContent();
        String jsonContent = XmlToJsonConverter.convert(xmlContent);
        result.setContent(jsonContent);
        return Optional.of(result);
    }

    @Override
    public Optional<ResultGetDto> findDtoById(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new ResultNotFoundException(id));
        String xmlContent = result.getContent();
        String jsonContent = XmlToJsonConverter.convert(xmlContent);
        JSONObject json = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject) parser.parse(jsonContent);
        }
        catch (ParseException e){
            System.out.println(e.getMessage());
        }

        ResultGetDto resultDto = new ResultGetDto(result.getId(),result.getContent(),result.getQueryInfo(),result.getContentList(),json);
        return Optional.of(resultDto);
    }

    @Override
    public Optional<ResultGetDto> findDtoByQueryId(Long id) {
        QueryInfo queryInfo = queryInfoRepository.getById(id);
        Result result = this.resultRepository.findByQueryInfo(queryInfo);

        String xmlContent = result.getContent();
        String jsonContent = XmlToJsonConverter.convert(xmlContent);
        JSONObject json = new JSONObject();
        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject) parser.parse(jsonContent);
        }
        catch (ParseException e){
            System.out.println(e.getMessage());
        }

        ResultGetDto resultDto = new ResultGetDto(result.getId(),result.getContent(),result.getQueryInfo(),result.getContentList(),json);
        return Optional.of(resultDto);
    }

    @Override
    public List<String> save(ResultDto resultDto) {
        QueryInfo queryInfo = queryInfoRepository.findById(resultDto.getQueryId()).orElseThrow(()-> new QueryNotFoundException(resultDto.getQueryId()));
        //Result result = new Result(resultDto.getResultSet(),query.getId());
      //  return Optional.of(endpointRepository.save(endpoint));
        return null;
    }

    @Override
    public Optional<Result> save(String content, Long queryId, List<String> contentList) {
        Result result = new Result(content, queryInfoRepository.getById(queryId),contentList);
        return Optional.of(this.resultRepository.save(result));
    }

    @Override
    public void deleteById(Long id) {
        this.resultRepository.deleteById(id);
    }
}
