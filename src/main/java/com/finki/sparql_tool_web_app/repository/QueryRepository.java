package com.finki.sparql_tool_web_app.repository;

import com.finki.sparql_tool_web_app.model.Query;
import com.finki.sparql_tool_web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Query,Long> {

    List<Query> findAllByUser(User user);

}
