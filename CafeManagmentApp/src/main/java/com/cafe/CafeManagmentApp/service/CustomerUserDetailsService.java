package com.cafe.CafeManagmentApp.service;


import com.cafe.CafeManagmentApp.exception.UserNotFoundException;
import com.cafe.CafeManagmentApp.model.User;
import com.cafe.CafeManagmentApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerUserDetailsService {



    @Autowired
    UserRepository userRepository;


    private  User userDetails;



    //Username is the Email
    public User loadUserByUsername(String email) throws UserNotFoundException {

         userDetails = userRepository.findByEmail(email);

        if (!Objects.isNull(userDetails)){
            return userDetails;
        }
        else{
            throw new UserNotFoundException("User not found");
        }
    }

    public User getUserDetails(){
        return userDetails;
    }


    public boolean isAdmin(){
        if(userDetails.getRole().equalsIgnoreCase("admin")){
            return true;
        }else {
            return false;
        }
    }


    public boolean isUser(){
        if(userDetails.getRole().equalsIgnoreCase("user")){
            return true;
        }else {
            return false;
        }
    }
    public boolean isActivatAccount(){
        if(userDetails.getStatus().equalsIgnoreCase("true")){
            return true;
        }else {
            return false;
        }
    }
}
