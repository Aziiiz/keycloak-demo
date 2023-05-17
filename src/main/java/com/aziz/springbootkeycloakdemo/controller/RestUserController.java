package com.aziz.springbootkeycloakdemo.controller;

import com.aziz.springbootkeycloakdemo.common.auth.AuthRequestContext;
import com.aziz.springbootkeycloakdemo.common.auth.Role;
import com.aziz.springbootkeycloakdemo.common.auth.RoleMapping;
import com.aziz.springbootkeycloakdemo.common.controller.BaseUserController;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/v1")
public class RestUserController  {

    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnoymus() {
        return ResponseEntity.ok("hello user");
    }

    @RoleMapping(Role.ADMIN)
    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin() {
        AuthRequestContext authRequestContext = new AuthRequestContext();
        String userName = authRequestContext.getUserName();
        return ResponseEntity.ok("Hello Admin \nUser Name : " + userName);
    }

    @RoleMapping({Role.USER, Role.ADMIN})
    @GetMapping("/user")
    public ResponseEntity<String> getUser() {
        AuthRequestContext authRequestContext = new AuthRequestContext();
        String userName = authRequestContext.getUserName();
        return ResponseEntity.ok("Hello User \nUser Name : " + userName);
    }
}
