package com.example.config.details;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EntityDetails {

    public static CustomUserDetails getCurrentUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        return details;
    }

    public static UUID getCurrentUserId() {
        return Objects.requireNonNull(getCurrentUserDetail()).getId();
    }


    public static List<String> getCurrentProfileRoleList() {
        CustomUserDetails details = getCurrentUserDetail();
        assert details != null;
        return details.getRoleList().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}