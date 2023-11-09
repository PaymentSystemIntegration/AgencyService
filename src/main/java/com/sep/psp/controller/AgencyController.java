package com.sep.psp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agency")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AgencyController {
    @GetMapping("/hello")
    public ResponseEntity<String> authenticate() {

        return ResponseEntity.ok().body("{\"token\": \"" + "Hello from agency microservice!" + "\"}");
    }
}
