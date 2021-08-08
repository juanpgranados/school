package com.challenge.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Value("${api.defaultu}")
    String defaultU;
    @Value("${api.defaultp}")
    String defaultP;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (defaultU.equals(username)) {
            return new User(username, defaultP, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
    }
}
