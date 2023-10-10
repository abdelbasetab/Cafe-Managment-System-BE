package com.cafe.CafeManagmentApp.rest;


import com.cafe.CafeManagmentApp.dto.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "user")
public interface UserRest {



    @PostMapping(path ="/signUp")
    ResponseEntity<String> signUp(@RequestBody Map<String, String> requestMap);

    @PostMapping(path ="/login")
     ResponseEntity<String> login(@RequestBody Map<String,String> requestMap);

    @GetMapping(path ="/get")
     ResponseEntity<List<UserWrapper>> getAllUser(@RequestParam  String email);


}
