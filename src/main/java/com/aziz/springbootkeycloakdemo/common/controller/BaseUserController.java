package com.aziz.springbootkeycloakdemo.common.controller;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;

public class BaseUserController {

    protected JwtAuthenticationToken getJwtAuthenticationToken(Principal principal) {
        return (JwtAuthenticationToken) principal;
    }

    protected String extractUsernameFromPrincipal(Principal principal) {
        JwtAuthenticationToken token = getJwtAuthenticationToken(principal);
        return (String) token.getTokenAttributes().get("name");
    }

    protected String extractUserEmailFromPrincipal(Principal principal) {
        JwtAuthenticationToken token = getJwtAuthenticationToken(principal);
        return (String) token.getTokenAttributes().get("email");
    }
}
