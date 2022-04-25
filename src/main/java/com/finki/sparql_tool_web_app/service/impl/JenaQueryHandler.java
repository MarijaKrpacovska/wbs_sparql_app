package com.finki.sparql_tool_web_app.service.impl;


import com.finki.sparql_tool_web_app.model.Endpoint;
import com.finki.sparql_tool_web_app.model.QueryInfo;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import java.util.ArrayList;
import java.util.List;

public class JenaQueryHandler {

    public static QueryType determineQueryType(QueryInfo queryInfo) {
        Query query = QueryFactory.create(queryInfo.getContent());
        return query.queryType();
    }

    public static List<String> getSelectQueryStringListResult(QueryInfo queryInfo){
        Endpoint endpoint = queryInfo.getEndpoint();
        Query query = QueryFactory.create(queryInfo.getContent());

        List<String> subjectStrings = query.getResultVars();

        List<String> list = new ArrayList();
        RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());
        QueryExecution qExec = conn.query(queryInfo.getContent()) ; //SELECT DISTINCT ?s where { [] a ?s } LIMIT 100

        ResultSet rs = qExec.execSelect() ;

        while (rs.hasNext()) {

            QuerySolution qs = rs.next();
            System.out.println("qs: "+qs);

            RDFNode rn = qs.get(subjectStrings.get(0)) ;
            System.out.print(qs.varNames());
            if(rn!= null) {
                if (rn.isLiteral()) {
                    Literal literal = qs.getLiteral(subjectStrings.get(0));
                    list.add(literal.toString());
                } else if (rn.isURIResource()) {
                    Resource subject = qs.getResource(subjectStrings.get(0));
                    System.out.println("Subject: " + subject.toString());
                    list.add(subject.toString());
                }
            }

        }
        return list;
    }

    public static String getSelectQueryStringResult(QueryInfo queryInfo){
        Endpoint endpoint = queryInfo.getEndpoint();
        Query query = QueryFactory.create(queryInfo.getContent());

        List<String> subjectStrings = query.getResultVars();

        List<String> list = new ArrayList();
        RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());
        QueryExecution qExec = conn.query(queryInfo.getContent()) ; //SELECT DISTINCT ?s where { [] a ?s } LIMIT 100

        ResultSet rs = qExec.execSelect() ;

        return ResultSetFormatter.asXMLString(rs);
    }
}
