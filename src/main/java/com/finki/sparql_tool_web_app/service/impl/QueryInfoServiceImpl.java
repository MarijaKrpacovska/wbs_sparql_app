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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    /*@Override
    public Optional<Query> save(QueryDto queryDto) {
        Endpoint endpoint = endpointRepository.findById(queryDto.getEndpointId()).orElseThrow(() -> new EndpointNotFoundException(queryDto.getEndpointId()));
        Query query = new Query(queryDto.getName(),
                queryDto.getContent(),
                endpoint);

        RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());
        QueryExecution qExec = conn.query(queryDto.getContent()) ; //SELECT DISTINCT ?s where { [] a ?s } LIMIT 100
        ResultSet rs = qExec.execSelect() ;
        while(rs.hasNext()) {
            QuerySolution qs = rs.next() ;
            Resource subject = qs.getResource("s") ;
            System.out.println("Subject: "+subject) ;
        }
        qExec.close() ;
        conn.close() ;

        return Optional.of(queryRepository.save(query));
    }*/

    /*@Override
    public List<String> save(QueryDto queryDto) {
        Endpoint endpoint = endpointRepository.findById(queryDto.getEndpointId()).orElseThrow(() -> new EndpointNotFoundException(queryDto.getEndpointId()));
        User user = userRepository.findByEmail(queryDto.getUserEmail());

        //  String prefix = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
        QueryInfo queryInfo = new QueryInfo(queryDto.getName(),
                queryDto.getContent(),
                endpoint,
                user,
                LocalDateTime.now());

        QueryInfo savedQueryInfo = queryInfoRepository.save(queryInfo);

        List<String> list = JenaQueryHandler.getSelectQueryResult(savedQueryInfo);
        String xmlStr = JenaQueryHandler.getSelectQueryStringResult(savedQueryInfo);

        //Result res = this.createResult(xmlStr,list, queryInfo).orElseThrow();
        //queryInfo.setQueryResultId(res.getId());
        queryInfo.setUniqueUrl("http://localhost:8090/api/queries/details/"+ queryInfo.getId());
        queryInfoRepository.save(queryInfo);

        return list;
    }*/

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

        List<String> list = JenaQueryHandler.getSelectQueryStringListResult(savedQueryInfo);
        String xmlStr = JenaQueryHandler.getSelectQueryStringResult(savedQueryInfo);
        queryInfo = this.createResult(xmlStr,list,savedQueryInfo);
        queryInfo.setUniqueUrl("http://localhost:8090/api/queries/details/"+ queryInfo.getId());
        QueryInfo finalQuery = queryInfoRepository.save(queryInfo);

        return Optional.of(finalQuery);
    }

    /*public List<String> getResult(QueryInfo queryInfo){
        Endpoint endpoint = queryInfo.getEndpoint();
        List<String> list = new ArrayList();
        RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());

        String subjectString = queryInfo.getContent().split("\\?")[1].split(" ")[0].split("\\R")[0];
        System.out.println("SUBJECT STRING" + subjectString);

        QueryExecution qExec = conn.query(queryInfo.getContent()) ; //SELECT DISTINCT ?s where { [] a ?s } LIMIT 100

        QueryType queryType = JenaQueryHandler.determineQueryType(queryInfo);
        QueryType queryType1 = queryType;
        ResultSet rs = qExec.execSelect() ;

        *//*qExec.close() ;
        conn.close() ;*//*
        while (rs.hasNext()) {

            //System.out.println(results.getResourceModel());
            //ResultSetFormatter.out(System.out,results, q);

            QuerySolution qs = rs.next();
            System.out.println("qs: "+qs);

            RDFNode rn = qs.get(subjectString) ;
            System.out.print(qs.varNames());
            if(rn!= null) {
                if (rn.isLiteral()) {
                    //String subject = qs.getResource(subjectString).toString();
                    //list.add(subject);
                    Literal literal = qs.getLiteral(subjectString);
                    list.add(literal.toString());
                } else if (rn.isURIResource()) {
                    Resource subject = qs.getResource(subjectString);
                    System.out.println("Subject: " + subject.toString());
                    list.add(subject.toString());
                }
            }

        }
        return list;
    }*/

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
