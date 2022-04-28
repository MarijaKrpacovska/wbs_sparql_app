package com.finki.sparql_tool_web_app.repository;

import com.finki.sparql_tool_web_app.model.Role;
import com.finki.sparql_tool_web_app.model.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> {

    SecureToken findByToken(final String token);

    Long removeByToken(String token);
}