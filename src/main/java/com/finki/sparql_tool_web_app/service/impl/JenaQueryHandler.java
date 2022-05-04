package com.finki.sparql_tool_web_app.service.impl;


import com.finki.sparql_tool_web_app.model.Endpoint;
import com.finki.sparql_tool_web_app.model.QueryInfo;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTP;
import org.apache.jena.sparql.exec.http.QueryExecutionHTTPBuilder;

import java.io.StringWriter;
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

    public static List<String> getDescribeQueryStringListResult(QueryInfo queryInfo){
        Endpoint endpoint = queryInfo.getEndpoint();
        Query query = QueryFactory.create(queryInfo.getContent());

        List<String> subjectStrings = query.getResultVars();

        List<String> list = new ArrayList();
        //  RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());
        QueryExecutionHTTP queryExecutionHTTP = QueryExecution.service(endpoint.getUrl(), queryInfo.getContent());
        //QueryExecution qExec = conn.query(queryInfo.getContent());
        Model descrModel = queryExecutionHTTP.execDescribe();

        System.out.print("model: ");
        descrModel.write(System.out);

        StmtIterator iter = descrModel.listStatements();

        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();
            Resource  subject   = stmt.getSubject();
            Property  predicate = stmt.getPredicate();
            RDFNode   object    = stmt.getObject();

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }
        return list;
    }

    public static String getDescribeQueryXMLStringResult(QueryInfo queryInfo){
        Endpoint endpoint = queryInfo.getEndpoint();
        Query query = QueryFactory.create(queryInfo.getContent());
        QueryExecutionHTTP queryExecutionHTTP = QueryExecution.service(endpoint.getUrl(), queryInfo.getContent());
        Model descrModel = queryExecutionHTTP.execDescribe();
        String syntax = "RDF/XML-ABBREV";
        StringWriter out = new StringWriter();
        descrModel.write(out, syntax);
        String result = out.toString();
        return result;
    }

    public static List<String> getConstructQueryStringListResult(QueryInfo queryInfo){
        Endpoint endpoint = queryInfo.getEndpoint();
        Query query = QueryFactory.create(queryInfo.getContent());

        List<String> subjectStrings = query.getResultVars();

        List<String> list = new ArrayList();
        //  RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());
        QueryExecutionHTTP queryExecutionHTTP = QueryExecution.service(endpoint.getUrl(), queryInfo.getContent());
        //QueryExecution qExec = conn.query(queryInfo.getContent());
        Model constructModel = queryExecutionHTTP.execConstruct();

        System.out.print("model: ");
        //constructModel.write(System.out);
        RDFList rdflist= constructModel.createList();

        StmtIterator iter = constructModel.listStatements();


        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();
            Resource  subject   = stmt.getSubject();
            Property  predicate = stmt.getPredicate();
            RDFNode   object    = stmt.getObject();
            String resStr="";

            System.out.print("subj: "+subject.toString());
            resStr += subject.toString();
            resStr += " ";
            System.out.print("predicate: " + predicate.toString() + " ");
            resStr += predicate.toString();
            resStr += " ";
            if (object instanceof Resource) {
                System.out.print("obj res: "+object.toString());
                resStr += object.toString();
                resStr += " ";
            } else {
                // object is a literal
                System.out.print("obj lit: \"" + object.toString() + "\"");
                resStr += object.toString();
                resStr += " ";
            }
            System.out.println(" .");
            list.add(resStr);
        }
        return list;
    }

    public static String getConstructQueryXmlStringResult(QueryInfo queryInfo){
        Endpoint endpoint = queryInfo.getEndpoint();
        Query query = QueryFactory.create(queryInfo.getContent());
        QueryExecutionHTTP queryExecutionHTTP = QueryExecution.service(endpoint.getUrl(), queryInfo.getContent());
        Model descrModel = queryExecutionHTTP.execConstruct();
        String syntax = "RDF/XML-ABBREV";
        StringWriter out = new StringWriter();
        descrModel.write(out, syntax);
        String result = out.toString();
        return result;
    }


    public static Boolean getAskQueryBooleanResult(QueryInfo queryInfo){
        Endpoint endpoint = queryInfo.getEndpoint();
        Query query = QueryFactory.create(queryInfo.getContent());

        List<String> subjectStrings = query.getResultVars();

        List<String> list = new ArrayList();
        //  RDFConnection conn = RDFConnectionFactory.connect(endpoint.getUrl());
        QueryExecutionHTTP queryExecutionHTTP = QueryExecution.service(endpoint.getUrl(), queryInfo.getContent());
        //QueryExecution qExec = conn.query(queryInfo.getContent());
        Boolean result = queryExecutionHTTP.execAsk();
        System.out.print(result);
        return result;
    }
}
