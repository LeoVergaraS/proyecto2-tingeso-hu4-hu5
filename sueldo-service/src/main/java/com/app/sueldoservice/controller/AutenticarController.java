package com.app.sueldoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.sueldoservice.models.TokenInfo;
import com.app.sueldoservice.models.UserInfo;
import com.app.sueldoservice.service.JwtUtilService;

@RestController
@RequestMapping("/autenticar")
public class AutenticarController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping
    public ResponseEntity<TokenInfo> aunthenticate (@RequestBody UserInfo userInfo){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userInfo.getUsuario(), userInfo.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUsuario());
        final String jwt = jwtUtilService.generateToken(userDetails);
        TokenInfo tokenInfo = new TokenInfo(jwt);
        return ResponseEntity.ok(tokenInfo);
    }

}
