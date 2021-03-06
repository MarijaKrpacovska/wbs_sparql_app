package com.finki.sparql_tool_web_app.repository;

import com.finki.sparql_tool_web_app.model.QueryInfo;
import com.finki.sparql_tool_web_app.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {

    Result findByQueryInfo(QueryInfo queryInfo);
}
