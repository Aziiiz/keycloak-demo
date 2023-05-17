package com.aziz.springbootkeycloakdemo.common.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AuthRequestContext {

    private String userName;

    public AuthRequestContext() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        this.userName = (String) authentication.getName();
    }

    public String getUserName() {
        return userName;
    }

}
