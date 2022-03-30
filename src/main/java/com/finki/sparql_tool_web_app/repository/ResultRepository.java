package com.finki.sparql_tool_web_app.repository;

import com.finki.sparql_tool_web_app.model.Query;
import com.finki.sparql_tool_web_app.model.Result;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {

    Result findByQuery(Query query);
}
