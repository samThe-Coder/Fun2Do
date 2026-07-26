package com.sampad.fun2do.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserPrincipal getCurrentUser(){

        Authentication authentication =
            SecurityContextHolder
            .getContext()
            .getAuthentication();

        return (UserPrincipal) authentication.getPrincipal();
    }
}
