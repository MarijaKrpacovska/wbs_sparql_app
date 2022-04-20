package com.finki.sparql_tool_web_app.service.impl;

import com.finki.sparql_tool_web_app.model.DTO.QueryDto;
import com.finki.sparql_tool_web_app.model.Endpoint;
import com.finki.sparql_tool_web_app.model.Query;
import com.finki.sparql_tool_web_app.model.Result;
import com.finki.sparql_tool_web_app.model.User;
import com.finki.sparql_tool_web_app.model.exceptions.EndpointNotFoundException;
import com.finki.sparql_tool_web_app.model.exceptions.QueryNotFoundException;
import com.finki.sparql_tool_web_app.repository.EndpointRepository;
import com.finki.sparql_tool_web_app.repository.QueryRepository;
import com.finki.sparql_tool_web_app.repository.UserRepository;
import com.finki.sparql_tool_web_app.service.QueryService;
import com.finki.sparql_tool_web_app.service.ResultService;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.LiteralImpl;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QueryServiceImpl implements QueryService {

    private final QueryRepository queryRepository;
    private final EndpointRepository endpointRepository;
    private final UserRepository userRepository;
    private final ResultService resultService;

    public QueryServiceImpl(QueryRepository queryRepository, EndpointRepository endpointRepository, UserRepository userRepository, ResultService resultService) {
        this.queryRepository = queryRepository;
        this.endpointRepository = endpointRepository;
        this.userRepository = userRepository;
        this.resultService = resultService;
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
        User user = userRepository.findByEmail(queryDto.getUserEmail());

        //  String prefix = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
        Query query = new Query(queryDto.getName(),
                queryDto.getContent(),
                endpoint,
                user,
                LocalDateTime.now());

        Query savedQuery = queryRepository.save(query);

        List<String> list = this.getResult(savedQuery);

        Result res = this.createResult(list,query).orElseThrow();
        query.setQueryResultId(res.getId());
        query.setUniqueUrl("http://localhost:8090/api/queries/details/"+query.getId());
        queryRepository.save(query);

        return list;
    }

    public List<String> getResult(Query query){
        Endpoint endpoint = query.getEndpoint();
        List<String> list = new ArrayList();
        RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());

        String subjectString = query.getContent().split("\\?")[1].split(" ")[0];
        System.out.println("SUBJECT STRING" + subjectString);

        QueryExecution qExec = conn.query(query.getContent()) ; //SELECT DISTINCT ?s where { [] a ?s } LIMIT 100

        ResultSet rs = qExec.execSelect() ;
        /*qExec.close() ;
        conn.close() ;*/
        while (rs.hasNext()) {

            //System.out.println(results.getResourceModel());
            //ResultSetFormatter.out(System.out,results, q);

            QuerySolution qs = rs.next();
            System.out.println("qs: "+qs);


            RDFNode rn = qs.get(subjectString) ;

            if (rn.isLiteral() ) {
                //String subject = qs.getResource(subjectString).toString();
                //list.add(subject);
                Literal literal = qs.getLiteral(subjectString);
                list.add(literal.toString());
            }
            else if (rn.isURIResource() ) {
                Resource subject = qs.getResource(subjectString);
                System.out.println("Subject: " + subject.toString());
                list.add(subject.toString());
            }

        }
        return list;
    }

    @Override
    public List<Query> findAllForUser(Long user) {
        return this.queryRepository.findAllByUser(userRepository.findById(user).orElseThrow());
    }

    @Override
    public List<Query> findAllForUser(String userEmail) {
        return this.queryRepository.findAllByUser(userRepository.findByEmail(userEmail));
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



    public Optional<Result> createResult(List<String> list, Query query){
        return this.resultService.save(list.toString(),query.getId(), list);
    }
}
