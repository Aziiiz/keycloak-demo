package com.aziz.springbootkeycloakdemo.common.auth;

import com.aziz.springbootkeycloakdemo.common.exception.Exception;
import com.aziz.springbootkeycloakdemo.common.exception.ExceptionErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Aspect
@Component
public class RoleMappingAspect {
    @Around("@annotation(roleMapping)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RoleMapping roleMapping) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!authentication.isAuthenticated()) {
            throw new Exception(ExceptionErrorCode.INSUFFICIENT_USER_ROLE);
        }

        for (Role role : roleMapping.value()) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role.name().toLowerCase()))) {
                return joinPoint.proceed();
            }
        }
        throw new Exception(ExceptionErrorCode.INSUFFICIENT_USER_ROLE, HttpStatus.UNAUTHORIZED);
    }
}
