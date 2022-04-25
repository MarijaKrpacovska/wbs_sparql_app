package com.finki.sparql_tool_web_app.repository;

import com.finki.sparql_tool_web_app.model.QueryInfo;
import com.finki.sparql_tool_web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryInfoRepository extends JpaRepository<QueryInfo,Long> {

    List<QueryInfo> findAllByUser(User user);

}
