package com.finki.sparql_tool_web_app.repository;

import com.finki.sparql_tool_web_app.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepository extends JpaRepository<Query,Long> {
}
