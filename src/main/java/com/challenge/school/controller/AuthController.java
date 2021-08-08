package com.challenge.school.controller;

import com.challenge.school.exception.ApiException;
import com.challenge.school.model.AuthRequest;
import com.challenge.school.model.AuthResponse;
import com.challenge.school.security.JwtTokenUtil;
import com.challenge.school.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUser(),
                    authRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUser());
            return new AuthResponse(jwtTokenUtil.generateToken(userDetails));
        } catch (DisabledException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST,"User disabled");
        } catch (BadCredentialsException e) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Credentials are not valid");
        }
    }
}
