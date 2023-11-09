package com.sep.psp.service;


import com.sep.psp.auth.interceptor.JwtService;
import com.sep.psp.dto.AuthenticationResponse;
import com.sep.psp.model.Role;
import com.sep.psp.model.User;
import com.sep.psp.repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthenticationService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequestFA request) {

        User user = userRepository.findByEmailAndPassword(request.getEmail(),request.getPassword());
        System.out.println("User:" + user.getEmail());
        var jwtToken = "";
        if(user == null)
            return AuthenticationResponse.builder()
                    .token(null)
                    .refreshToken(null)
                    .build();

        Map<String, Object> roleMap = new HashMap<>();
        System.out.println("Duzina rola:" + user.getRoles().size());
        for(Role role : user.getRoles())
        {
            roleMap.put("role",role.getName());
        }
        jwtToken = jwtService.generateToken(roleMap,user);
        System.out.println("Token:" + jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



}
