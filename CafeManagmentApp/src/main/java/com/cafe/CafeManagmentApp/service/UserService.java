package com.cafe.CafeManagmentApp.service;


import com.cafe.CafeManagmentApp.controller.UserController;
import com.cafe.CafeManagmentApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {



    @Autowired
    UserRepository userRepository;



}
