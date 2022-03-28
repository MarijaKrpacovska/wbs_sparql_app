package com.finki.sparql_tool_web_app.service.impl;

import com.finki.sparql_tool_web_app.model.DTO.QueryDto;
import com.finki.sparql_tool_web_app.model.Endpoint;
import com.finki.sparql_tool_web_app.model.Query;
import com.finki.sparql_tool_web_app.model.exceptions.EndpointNotFoundException;
import com.finki.sparql_tool_web_app.model.exceptions.QueryNotFoundException;
import com.finki.sparql_tool_web_app.repository.EndpointRepository;
import com.finki.sparql_tool_web_app.repository.QueryRepository;
import com.finki.sparql_tool_web_app.service.QueryService;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QueryServiceImpl implements QueryService {

    private final QueryRepository queryRepository;
    private final EndpointRepository endpointRepository;

    public QueryServiceImpl(QueryRepository queryRepository, EndpointRepository endpointRepository) {
        this.queryRepository = queryRepository;
        this.endpointRepository = endpointRepository;
    }

    @Override
    public List<Query> findAll() {
        return queryRepository.findAll();
    }

    @Override
    public Optional<Query> findById(Long id) {
        return queryRepository.findById(id);
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

    @Override
    public List<String> save(QueryDto queryDto) {
        Endpoint endpoint = endpointRepository.findById(queryDto.getEndpointId()).orElseThrow(() -> new EndpointNotFoundException(queryDto.getEndpointId()));
        List<String> list = new ArrayList();

        Query query = new Query(queryDto.getName(),
                queryDto.getContent(),
                endpoint);

        RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());
        QueryExecution qExec = conn.query(queryDto.getContent()) ; //SELECT DISTINCT ?s where { [] a ?s } LIMIT 100
        String subjectString = queryDto.getContent().split("\\?")[1].split(" ")[0];
        System.out.println("SUBJECT STRING" + subjectString);
        ResultSet rs = qExec.execSelect() ;
        /*qExec.close() ;
        conn.close() ;*/
        while (rs.hasNext()) {

            //System.out.println(results.getResourceModel());
            //ResultSetFormatter.out(System.out,results, q);
            QuerySolution qs = rs.next();
            Resource subject = qs.getResource(subjectString) ;
            System.out.println("Subject: "+subject.toString()) ;
            list.add(subject.toString());
        }


        queryRepository.save(query);
        return list;
    }

    @Override
    public Optional<Query> edit(Long id, QueryDto queryDto) {
        Query query = this.findById(id).orElseThrow(() -> new QueryNotFoundException(id));
        query.setName(queryDto.getName());
        query.setContent(queryDto.getContent());
        query.setEndpoint(this.endpointRepository.findById(queryDto.getEndpointId())
                .orElseThrow(
                    () -> new EndpointNotFoundException(queryDto.getEndpointId())
                ));
        queryRepository.save(query);
        return Optional.of(query);
    }

    @Override
    public void deleteById(Long id) {
        this.queryRepository.deleteById(id);
    }
}
