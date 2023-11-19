package com.sep.psp.controller;

import com.sep.psp.dto.AuthenticationResponse;
import com.sep.psp.service.AuthenticationRequestFA;
import com.sep.psp.service.AuthenticationService;
import com.sep.psp.service.definition.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequestFA request) {
        System.out.println(request.getEmail() + " " + request.getPassword());
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
