package com.sep.psp.controller;

import com.sep.psp.dto.AuthenticationResponse;
import com.sep.psp.service.AuthenticationRequestFA;
import com.sep.psp.service.AuthenticationService;
import com.sep.psp.service.definition.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping(path="/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequestFA request) {
        System.out.println(request.getEmail() + " " + request.getPassword());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        String token = "";
        if(authenticationResponse.getToken() != null)
            token = authenticationResponse.getToken();

        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }
    @GetMapping(path = "/current")
    public ResponseEntity<?> getCurrentUser() {
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }
}
