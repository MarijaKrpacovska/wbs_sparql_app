package com.finki.sparql_tool_web_app.service.impl;

import com.finki.sparql_tool_web_app.model.DTO.QueryDto;
import com.finki.sparql_tool_web_app.model.Endpoint;
import com.finki.sparql_tool_web_app.model.QueryInfo;
import com.finki.sparql_tool_web_app.model.Result;
import com.finki.sparql_tool_web_app.model.User;
import com.finki.sparql_tool_web_app.model.exceptions.EndpointNotFoundException;
import com.finki.sparql_tool_web_app.model.exceptions.QueryNotFoundException;
import com.finki.sparql_tool_web_app.repository.EndpointRepository;
import com.finki.sparql_tool_web_app.repository.QueryInfoRepository;
import com.finki.sparql_tool_web_app.repository.UserRepository;
import com.finki.sparql_tool_web_app.service.QueryInfoService;
import com.finki.sparql_tool_web_app.service.ResultService;
import org.apache.jena.query.QueryType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QueryInfoServiceImpl implements QueryInfoService {

    private final QueryInfoRepository queryInfoRepository;
    private final EndpointRepository endpointRepository;
    private final UserRepository userRepository;
    private final ResultService resultService;

    public QueryInfoServiceImpl(QueryInfoRepository queryInfoRepository, EndpointRepository endpointRepository, UserRepository userRepository, ResultService resultService) {
        this.queryInfoRepository = queryInfoRepository;
        this.endpointRepository = endpointRepository;
        this.userRepository = userRepository;
        this.resultService = resultService;
    }

    @Override
    public List<QueryInfo> findAll() {
        return queryInfoRepository.findAll();
    }

    @Override
    public Optional<QueryInfo> findById(Long id) {
        return queryInfoRepository.findById(id);
    }


    @Override
    public List<String> saveOld(QueryDto queryDto) {
        Endpoint endpoint = endpointRepository.findById(queryDto.getEndpointId()).orElseThrow(() -> new EndpointNotFoundException(queryDto.getEndpointId()));
        User user = userRepository.findByEmail(queryDto.getUserEmail());

        QueryInfo queryInfo = new QueryInfo(queryDto.getName(),
                queryDto.getContent(),
                endpoint,
                user,
                LocalDateTime.now());

        QueryInfo savedQueryInfo = queryInfoRepository.save(queryInfo);

        List<String> list = JenaQueryHandler.getSelectQueryStringListResult(savedQueryInfo);
        String xmlStr = JenaQueryHandler.getSelectQueryStringResult(savedQueryInfo);
        queryInfo = this.createResult(xmlStr,list,savedQueryInfo);
        queryInfo.setUniqueUrl("http://localhost:8090/api/queries/details/"+ queryInfo.getId());
        queryInfoRepository.save(queryInfo);

        return list;
    }

    //todo: add QueryType to queryInfo entity
    //todo: handle invalid queries and saving into DB only when query is valid
    @Override
    public Optional<QueryInfo> save(QueryDto queryDto) {
        Endpoint endpoint = endpointRepository.findById(queryDto.getEndpointId()).orElseThrow(() -> new EndpointNotFoundException(queryDto.getEndpointId()));
        User user = userRepository.findByEmail(queryDto.getUserEmail());

        QueryInfo queryInfo = new QueryInfo(queryDto.getName(),
                queryDto.getContent(),
                endpoint,
                user,
                LocalDateTime.now());

        QueryInfo savedQueryInfo = queryInfoRepository.save(queryInfo);

        QueryType queryType = JenaQueryHandler.determineQueryType(savedQueryInfo);

        List<String> list = null;
        String xmlStr = null;
        if(queryType.equals(QueryType.SELECT)) {
            list = JenaQueryHandler.getSelectQueryStringListResult(savedQueryInfo);
            xmlStr = JenaQueryHandler.getSelectQueryStringResult(savedQueryInfo);
        }
        else if(queryType.equals(QueryType.DESCRIBE)){
            list = JenaQueryHandler.getDescribeQueryStringListResult(savedQueryInfo);
            xmlStr = JenaQueryHandler.getDescribeQueryXMLStringResult(savedQueryInfo);
        }
        else if(queryType.equals(QueryType.CONSTRUCT)){
            list = JenaQueryHandler.getConstructQueryStringListResult(savedQueryInfo);
            xmlStr = JenaQueryHandler.getConstructQueryXmlStringResult(savedQueryInfo);
        }
        else if(queryType.equals(QueryType.ASK)){
            xmlStr = Boolean.toString(JenaQueryHandler.getAskQueryBooleanResult(savedQueryInfo));
        }
        else if(queryType.equals(QueryType.UNKNOWN)){
            System.out.print("Unknown query type");
        }
        else {
            System.out.print("Can't execute query");
        }
        queryInfo = this.createResult(xmlStr,list,savedQueryInfo);
        queryInfo.setUniqueUrl("http://localhost:8090/api/queries/details/"+ queryInfo.getId());
        QueryInfo finalQuery = queryInfoRepository.save(queryInfo);

        return Optional.of(finalQuery);
    }

    @Override
    public List<QueryInfo> findAllForUser(Long user) {
        return this.queryInfoRepository.findAllByUser(userRepository.findById(user).orElseThrow());
    }

    @Override
    public List<QueryInfo> findAllForUser(String userEmail) {
        return this.queryInfoRepository.findAllByUser(userRepository.findByEmail(userEmail));
    }

    @Override
    public Optional<QueryInfo> edit(Long id, QueryDto queryDto) {
        QueryInfo queryInfo = this.findById(id).orElseThrow(() -> new QueryNotFoundException(id));
        queryInfo.setName(queryDto.getName());
        queryInfo.setContent(queryDto.getContent());
        queryInfo.setEndpoint(this.endpointRepository.findById(queryDto.getEndpointId())
                .orElseThrow(
                        () -> new EndpointNotFoundException(queryDto.getEndpointId())
                ));
        queryInfoRepository.save(queryInfo);
        return Optional.of(queryInfo);
    }

    @Override
    public void deleteById(Long id) {
        this.queryInfoRepository.deleteById(id);
    }

    public QueryInfo createResult(String stringRes, List<String> list, QueryInfo queryInfo){
        Result res = this.resultService.save(stringRes, queryInfo.getId(), list).orElseThrow();
        queryInfo.setQueryResultId(res.getId());
        return queryInfo;
    }
}
