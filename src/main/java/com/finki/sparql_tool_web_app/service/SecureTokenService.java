package com.finki.sparql_tool_web_app.service;

import com.finki.sparql_tool_web_app.model.SecureToken;

public interface SecureTokenService {

    SecureToken createSecureToken();
    void saveSecureToken(final SecureToken token);
    SecureToken findByToken(final String token);
    void removeToken(final SecureToken token);
    void removeTokenByToken(final String token);
}