package com.sep.psp.service.implementation;

import com.sep.psp.auth.SecurityUtils;
import com.sep.psp.dto.UserDTO;
import com.sep.psp.model.User;
import com.sep.psp.repo.IUserRepository;
import com.sep.psp.service.AuthenticationService;
import com.sep.psp.service.definition.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    IUserRepository userRepository;


    @Override
    public User getUserById(Integer id) {return userRepository.getById(id);}

    private UserDTO userDTO;
    public void setUserInfo(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public UserDTO getUserInfo() {
        return userDTO;
    }
    @Override
    public User getCurrentUser() {

        String email = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.findOneByEmail(email);
    }



}