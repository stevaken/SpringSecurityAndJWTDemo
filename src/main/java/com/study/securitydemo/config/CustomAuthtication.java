package com.study.securitydemo.config;

import com.study.securitydemo.service.impl.GrantedAuthorityImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class CustomAuthtication implements AuthenticationProvider {
    private CustomUserDetailService userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthtication(CustomUserDetailService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        if (null != userDetails) {
            if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                List<GrantedAuthority> list = new ArrayList<>();
                list.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
                list.add(new GrantedAuthorityImpl("AUTH_WRITE"));
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(name, password, list);
                return auth;
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
