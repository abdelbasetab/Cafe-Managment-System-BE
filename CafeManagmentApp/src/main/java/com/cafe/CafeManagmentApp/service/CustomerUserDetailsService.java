package com.cafe.CafeManagmentApp.service;


import com.cafe.CafeManagmentApp.model.User;
import com.cafe.CafeManagmentApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService {



    @Autowired
    UserRepository userRepository;



    //Username is the Email
    public User loadUserByUsername(String email){

        User userDetails = userRepository.findByEmail(email);
        return userDetails;
    }
}
