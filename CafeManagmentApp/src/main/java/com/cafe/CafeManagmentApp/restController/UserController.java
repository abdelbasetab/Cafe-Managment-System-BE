package com.cafe.CafeManagmentApp.restController;

import com.cafe.CafeManagmentApp.constents.CafeConstents;
import com.cafe.CafeManagmentApp.dto.UserWrapper;
import com.cafe.CafeManagmentApp.rest.UserRest;
import com.cafe.CafeManagmentApp.service.UserService;
import com.cafe.CafeManagmentApp.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController implements UserRest {

    @Autowired
    UserService userService;




    //update User Data
    //todo
    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{

            return userService.update(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();


        }
        return null;
    }


    //get all user from database ,if the current user is an Admin
    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser(@RequestParam String email) {
        try{

            return userService.getAllUser(email);


        }catch (Exception ex){

            ex.printStackTrace();

        }
        // when something went wrong
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {

            return userService.signUp(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {

            return userService.login(requestMap);

        }catch (Exception ex){

            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
