package com.cafe.CafeManagmentApp.service;


import com.cafe.CafeManagmentApp.constents.CafeConstents;
import com.cafe.CafeManagmentApp.exception.UserNotFoundException;
import com.cafe.CafeManagmentApp.model.User;
import com.cafe.CafeManagmentApp.repository.UserRepository;
import com.cafe.CafeManagmentApp.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UserService {



    @Autowired
    UserRepository userRepository;


    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        try{

            if (verifySignUpMap(requestMap)){
                //check later if its work
                User user = userRepository.findByEmail(requestMap.get("email"));

                if (Objects.isNull(user)){
                    User userFromMapReq = getUserFromMap(requestMap);
                    //save in the database
                    userRepository.save(userFromMapReq);
                    return CafeUtils.getResponseEntity(CafeConstents.SUCCEFUL_REGISTERT,HttpStatus.OK);
                }
                else {
                    return CafeUtils.getResponseEntity(CafeConstents.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return CafeUtils.getResponseEntity(CafeConstents.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private User getUserFromMap(Map<String, String> requestMap){

        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");//default is false
        user.setRole("user");//default is user
        return user;

    }




    //verify if the Request Map has all the data that we need

    private boolean verifySignUpMap(Map<String, String> requestMap){

        if (requestMap.containsKey("name")
                && requestMap.containsKey("email")
                &&requestMap.containsKey("password")
                &&requestMap.containsKey("contactNumber")) {
            return true;
        }
        else {
            return false;
        }


    }


    //+++++++ From hier ist not finished

    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{

            return null;

        }catch (Exception ex ){
            ex.printStackTrace();
            return CafeUtils.getResponseEntity(CafeConstents.INVALID_DATA,HttpStatus.BAD_REQUEST);
        }
    }



    public User getUserDetails(String email) throws UserNotFoundException {
      User userDetails = userRepository.findByEmail(email);
      if (!Objects.isNull(userDetails)){
          return userDetails;
      }
      else {
          throw new UserNotFoundException("User not found in the Database");
      }
    }



}
